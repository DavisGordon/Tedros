/**
 * 
 */
package org.tedros.chat.module.client.behaviour;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITModelForm;
import org.tedros.chat.CHATKey;
import org.tedros.chat.ejb.controller.IChatController;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.model.Action;
import org.tedros.chat.model.ChatInfo;
import org.tedros.chat.module.client.decorator.ChatDecorator;
import org.tedros.chat.module.client.model.ChatMV;
import org.tedros.chat.module.client.model.ChatUserMV;
import org.tedros.chat.module.client.setting.ChatClient;
import org.tedros.chat.module.client.setting.ChatUtil;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.control.TFileField;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
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
	
	@Override
	public void load() {
		super.load();
		util = new ChatUtil();
		client = ChatClient.getInstance();
		deco = (ChatDecorator) super.getPresenter().getDecorator();
			// Listen for received message
		ChangeListener<Object> chl1 = (a,o,n) -> {
			if(n instanceof ChatInfo) {
				ChatInfo ci = (ChatInfo) n;
				if(ci.getAction().equals(Action.DELETE)) {
					ChatMV mv = super.getModelView();
					if(mv!=null && mv.getId().getValue().equals(ci.getId())) {
						super.addMessage(new TMessage(TLanguage.getInstance()
								.getString(CHATKey.MSG_OWNER_REMOVED_CHAT), "Ok", 
							ev-> {
							super.remove();
							super.getView().tHideModal();
						}));
					}else {
						Optional<ChatMV> op = super.getModels().stream()
							.filter(p->{
								return p.getId().getValue().equals(ci.getId());
							}).findFirst();
						if(op.isPresent()) {
							ChatMV m = op.get();
							super.addMessage(new TMessage(TLanguage.getInstance()
									.getFormatedString(CHATKey.MSG_CHAT_REMOVED,
											m.getTitle().getValue()), "Ok", 
									ev-> {
									super.getModels().remove(m);
									super.getView().tHideModal();
							}));
						}
					}
				}
			}
			if(n instanceof ChatMessage) {
				ChatMessage cm = (ChatMessage) n;
				Optional<ChatMV> op = deco.gettListView().getItems()
					.stream().filter(p->{
						return p.getId().getValue().equals(cm.getChat().getId());
					}).findFirst();
				if(op.isPresent()) {
					op.get().getMessages().add(cm);
				}else {
					TEntityProcess<Chat> p = new TEntityProcess<Chat>(Chat.class, IChatController.JNDI_NAME) {};
					p.stateProperty().addListener((x,y,s)->{
						if(s.equals(State.SUCCEEDED)) {
							TResult<Chat> res = p.getValue().get(0);
							Chat c = res.getValue();
							ChatMV cmv = new ChatMV(c);
							deco.gettListView().getItems().add(cmv);
						}
					});
					p.findById(cm.getChat());
					p.startProcess();
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
		
		ChangeListener<Boolean> bolChl = (a,o,n)->{
			this.hidePopOver.setValue(n);
		};
		
		ChangeListener<ITModelForm<ChatMV>> chl0 = (a,o,n)->{
			if(o!=null) {
				TFileField ff = (TFileField) o.gettFieldDescriptorList().stream()
					.filter(p->{
							return p.getFieldName().equals("sendFile");
					}).findFirst().get().getControl();
				ff.fileChooserOpenedProperty().removeListener(bolChl);
			}
			if(n!=null) {
				n.tLoadedProperty().addListener((x,y,z)->{
					if(z) {
						TFileField ff = (TFileField) n.gettFieldDescriptorList().stream()
							.filter(p->{
								return p.getFieldName().equals("sendFile");
							}).findFirst().get().getControl();
						ff.fileChooserOpenedProperty().addListener(bolChl);
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
	
	public void setHidePopOver(boolean hide) {
		this.hidePopOver.setValue(hide);
	}

	/**
	 * @return the hidePopOver
	 */
	public ReadOnlyBooleanProperty hidePopOverProperty() {
		return hidePopOver;
	}
	
}
