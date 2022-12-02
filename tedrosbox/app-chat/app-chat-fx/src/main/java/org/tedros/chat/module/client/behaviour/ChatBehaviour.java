/**
 * 
 */
package org.tedros.chat.module.client.behaviour;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.tedros.api.form.ITModelForm;
import org.tedros.chat.CHATKey;
import org.tedros.chat.ejb.controller.IChatController;
import org.tedros.chat.ejb.controller.IChatMessageController;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.model.Action;
import org.tedros.chat.model.ChatInfo;
import org.tedros.chat.module.client.decorator.ChatDecorator;
import org.tedros.chat.module.client.model.ChatMV;
import org.tedros.chat.module.client.model.ChatUserMV;
import org.tedros.chat.module.client.setting.ChatClient;
import org.tedros.chat.module.client.setting.ChatFormSetting;
import org.tedros.chat.module.client.setting.ChatUtil;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.control.TFileField;
import org.tedros.fx.control.TPickListField;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class ChatBehaviour extends TMasterCrudViewBehavior<ChatMV, Chat> {

	private ChatUtil util;
	private ChatClient client;
	private ChatDecorator deco;
	private SimpleBooleanProperty hidePopOver = new SimpleBooleanProperty(false);
	
	private SimpleLongProperty totalUnreadMessages = new SimpleLongProperty(0);
	
	@SuppressWarnings("unchecked")
	@Override
	public void load() {
		super.load();
		util = new ChatUtil();
		client = ChatClient.getInstance();
		deco = (ChatDecorator) super.getPresenter().getDecorator();
		// Listen for received message
		ChangeListener<Object> chl1 = (a,o,n) -> {
			//Chat info
			if(n instanceof ChatInfo) {
				ChatInfo ci = (ChatInfo) n;
				// check if the chat is opened
				ChatMV mv = super.getModelView();
				if(mv!=null && mv.getId().getValue().equals(ci.getId())) {
					switch(ci.getAction()) {
					case DELETE: // message the chat was removed
						super.addMessage(new TMessage(TLanguage.getInstance()
								.getString(CHATKey.MSG_OWNER_REMOVED_CHAT), "Ok", 
							ev-> {
							super.remove();
							super.getView().tHideModal();
						}));
						break;
					case UPDATE_RECIPIENT: // update the chat users
						ITModelForm<ChatMV> f = super.getForm();
						ChatFormSetting fs = (ChatFormSetting) f.gettSetting();
						fs.removeRecipientsListener();
						TPickListField<ChatUserMV> plf = (TPickListField<ChatUserMV>) f.gettFieldBox("participants").gettControl();
						List<ChatUserMV> add = plf.gettSourceList().stream()
								.filter(p->ci.getRecipients().stream().anyMatch(p1->p1.getId().equals(p.getModel().getId())))
								.collect(Collectors.toList());
						if(add!=null && add.size()>0) {
							plf.gettSourceListView().getItems().removeAll(add);
							plf.gettSelectedListView().getItems().addAll(add);
						}
						
						List<ChatUserMV> rem = plf.gettSelectedList().stream()
						.filter(p->ci.getRecipients().stream().noneMatch(p1->p1.equals(p.getModel())))
						.collect(Collectors.toList());
						if(rem!=null && rem.size()>0) {
							plf.gettSelectedListView().getItems().removeAll(rem);
							plf.gettSourceListView().getItems().addAll(rem);
						}
						fs.buildTitle(mv.getParticipants());
						fs.addRecipientsListener();
						break;
					}
				}else {
					// look for the chat in the list view
					Optional<ChatMV> op = super.getModels().stream()
						.filter(p->{
							return p.getId().getValue().equals(ci.getId());
						}).findFirst();
					if(op.isPresent()) {
						// chat found
						ChatMV m = op.get();
						switch(ci.getAction()) {
						case DELETE: // message the chat was removed
							super.addMessage(new TMessage(TLanguage.getInstance()
									.getFormatedString(CHATKey.MSG_CHAT_REMOVED,
											m.getTitle().getValue()), "Ok", 
									ev-> {
									super.getModels().remove(m);
									super.getView().tHideModal();
							}));
							break;
						case UPDATE_RECIPIENT: // update the chat users
							m.getParticipants().clear();
							ci.getRecipients().forEach(u->{
								ChatUserMV umv = new ChatUserMV(u);
								m.getParticipants().add(umv);
							});
							break;
						}
					}
				}
			}
			//received a chat message
			if(n instanceof ChatMessage) {
				ChatMessage cm0 = (ChatMessage) n;
				// set the message as received 
				cm0.addReceived(client.getOwner());
				//...Look for the Chat of the message
				Optional<ChatMV> op = super.getModels().stream()
						.filter(p->{
							return p.getId().getValue().equals(cm0.getChat().getId());
						}).findFirst();
				// add the message in the chat
				if(op.isPresent()) {
					ChatMV found = op.get();
					ChatMV current = super.getModelView();
					
					// set the message as viewed
					if(current!=null && current.equals(found)) 
						cm0.addViewed(client.getOwner());
					
					//build a process to save and execute the message
					TEntityProcess<ChatMessage> cmp = 
							new TEntityProcess<ChatMessage>(ChatMessage.class, 
									IChatMessageController.JNDI_NAME) {};
					cmp.stateProperty().addListener((x0,y0,s0)->{
						if(s0.equals(State.SUCCEEDED)) {
							TResult<ChatMessage> res0 = cmp.getValue().get(0);
							ChatMessage cm1 = res0.getValue();
							if(found.isMessagesLoaded())
								found.getMessages().add(cm1);
							else {
								found.increaseTotalMessages();
								if(current!=null && current.equals(found)) 
									found.increaseViewedMessages();
							}
							this.countUnreadMessages();
						}
					});
					cmp.save(cm0);
					cmp.startProcess();
				}else {
					//chat not found
					//build a process to save the message as received 
					//and get the chat to add in the list
					TEntityProcess<ChatMessage> cmp = 
							new TEntityProcess<ChatMessage>(ChatMessage.class, 
									IChatMessageController.JNDI_NAME) {};
					cmp.stateProperty().addListener((x0,y0,s0)->{
						if(s0.equals(State.SUCCEEDED)) {
							TResult<ChatMessage> res0 = cmp.getValue().get(0);
							ChatMessage cm1 = res0.getValue();
							
							TEntityProcess<Chat> p = new TEntityProcess<Chat>(Chat.class, 
									IChatController.JNDI_NAME) {};
							p.stateProperty().addListener((x1,y1,s1)->{
								if(s1.equals(State.SUCCEEDED)) {
									TResult<Chat> res1 = p.getValue().get(0);
									Chat c = res1.getValue();
									ChatMV cmv = new ChatMV(c);
									super.getModels().add(cmv);
								}
							});
							p.findById(cm1.getChat());
							p.startProcess();
						}
					});
					cmp.save(cm0);
					cmp.startProcess();
				}
			}
		};
		super.getListenerRepository().add("chl1", chl1);
		client.messageProperty().addListener(new WeakChangeListener<>(chl1));
		
		super.addAction(new TPresenterAction(TActionType.DELETE) {
			
			private Long chatId;
			private List<ChatUserMV> recipients;

			@Override
			public boolean runBefore() {
				ChatMV mv = getModelView();
				if(!mv.getOwner().getValue().getId().equals(client.getOwner().getId())) {
					addMessage(new TMessage(TMessageType.WARNING, 
							TLanguage.getInstance()
							.getString(CHATKey.MSG_ONLY_OWNER_DELETE)
							));
					return false;
				}
				chatId = mv.getId().getValue();
				recipients = mv.getParticipants();
				return true;
			}

			@Override
			public void runAfter() {
				ChatInfo i = new ChatInfo();
				i.setId(chatId);
				i.setUser(client.getOwner());
				i.setAction(Action.DELETE);
				this.recipients.forEach(c->{
					i.addRecipient(c.getEntity());
				});
				
				try {
					client.send(i);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		ChangeListener<Boolean> chl = (a,o,n)->{
			if(n && super.getView().tModalVisibleProperty().getValue())
				super.getView().tHideModal();
			else if(!n && !super.getView().tModalVisibleProperty().getValue())
				showLogMessage();
		};
		super.getListenerRepository().add("msgModalchl", chl);
		client.connectedProperty().addListener(new WeakChangeListener<>(chl));
	
		showLogMessage();
		
		ChangeListener<ITModelForm<ChatMV>> chl0 = (a,o,n)->{
			if(n!=null) {
				n.tLoadedProperty().addListener((x,y,z)->{
					if(z) {
						TFileField ff = (TFileField) n.gettFieldDescriptorList().stream()
							.filter(p->{
								return p.getFieldName().equals("sendFile");
							}).findFirst().get().getControl();
						ChangeListener<Boolean> bolChl = (a0,o0,n0)->{
							this.hidePopOver.setValue(n0);
						};
						n.gettObjectRepository().add("sendFile_chl", bolChl);
						ff.fileChooserOpenedProperty().addListener(new WeakChangeListener<>(bolChl));
					}
				});
			}
		};
		super.getListenerRepository().add("chl0", chl0);
		super.formProperty().addListener(new WeakChangeListener<>(chl0));
	}
	
	private void showLogMessage() {
		if(!client.isConnected())
			super.getView().tShowModal(new TMessageBox(Arrays.asList(buildLogMessage())), false);
	}
	
	public void countUnreadMessages() {
		Platform.runLater(()->{
			long total = 0;
			for(ChatMV cmv : getModels()) 
				total += cmv.countTotalUnreadMessages();
			this.totalUnreadMessages.setValue(total);

		});
				
	}
	
	private void subtractFrom(ChatMV cmv) {
		Platform.runLater(()->{
			long total = cmv.countTotalUnreadMessages();
			this.totalUnreadMessages
				.setValue(this.totalUnreadMessages.getValue()-total);
		});
	}
	
	@Override
	protected void configListViewChangeListener() {
		
		ListChangeListener<ChatMV> lcl = c ->{
			if(c.next()) {
				if(c.wasAdded())
					this.countUnreadMessages();
				if(c.wasRemoved())
					c.getRemoved().stream()
					.forEach(cmv->{
						this.subtractFrom(cmv);
					});
			}
		};
		super.getListenerRepository().add("chatlistviewcountListener", lcl);
		super.getModels().addListener(new WeakListChangeListener<>(lcl));
		super.configListViewChangeListener();
	}
	
	private TMessage  buildLogMessage() {
		return new TMessage(client.getLog(), iEngine.getString(CHATKey.CONNECT), 
			c->{
				if(!client.isConnected()) 
					client.connect();
			});
	}
	
	
	@Override
	public void setDisableModelActionButtons(boolean flag) {
		super.setDisableModelActionButtons(flag);
		if(!flag && !super.getModelView().getOwner().getValue().getId().equals(client.getOwner().getId()))
			deco.gettDeleteButton().setDisable(true);
	}
	
	@Override
	public boolean processNewEntityBeforeBuildForm(ChatMV model) {
		try {
			model.getModel().setCode(UUID.randomUUID().toString());
			model.getOwner().setValue(ChatClient.getInstance().getOwner());
			Chat c = util.saveChat(TedrosContext.getLoggedUser().getAccessToken(), model.getModel());
			model.reload(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.processNewEntityBeforeBuildForm(model);
	}
	
	@Override
	public void cancelAction() {
		setModelView(null);
		colapseAction();
		deco.gettListView().getSelectionModel().clearSelection();
	}
	
	public void setHidePopOver(boolean hide) {
		this.hidePopOver.setValue(hide);
	}

	/**
	 * @return the hidePopOver
	 */
	public ReadOnlyBooleanProperty hidePopOverProperty() {
		return hidePopOver;
	}

	/**
	 * @return the totalUnreadMessages
	 */
	public ReadOnlyLongProperty totalUnreadMessagesProperty() {
		return totalUnreadMessages;
	}
	
}
