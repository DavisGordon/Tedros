/**
 * 
 */
package org.tedros.chat.module.client.behaviour;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.tedros.chat.CHATKey;
import org.tedros.chat.ejb.controller.IChatController;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.model.Action;
import org.tedros.chat.model.ChatInfo;
import org.tedros.chat.module.client.TChatModule;
import org.tedros.chat.module.client.model.ChatUserMV;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.chat.module.client.setting.ChatClient;
import org.tedros.chat.module.client.setting.ChatUtil;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.result.TResult;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class TChatBehaviour extends TMasterCrudViewBehavior<TChatMV, Chat> {

	private ChatUtil util;
	private ChatClient client;
	TMasterCrudViewDecorator<TChatMV> deco;
	
	@Override
	public void load() {
		super.load();
		util = new ChatUtil();
		client = ChatClient.getInstance();
		deco = (TMasterCrudViewDecorator<TChatMV>) super.getPresenter().getDecorator();
			// Listen for received message
		ChangeListener<Object> chl1 = (a,o,n) -> {
			if(n instanceof ChatInfo) {
				ChatInfo ci = (ChatInfo) n;
				if(ci.getAction().equals(Action.DELETE)) {
					TChatMV mv = super.getModelView();
					if(mv!=null && mv.getId().getValue().equals(ci.getId())) {
						super.addMessage(new TMessage(TLanguage.getInstance()
								.getString(CHATKey.MSG_OWNER_REMOVED_CHAT), "Ok", 
							ev-> {
							super.remove();
							super.getView().tHideModal();
						}));
					}else {
						Optional<TChatMV> op = super.getModels().stream()
							.filter(p->{
								return p.getId().getValue().equals(ci.getId());
							}).findFirst();
						if(op.isPresent()) {
							TChatMV m = op.get();
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
				Optional<TChatMV> op = deco.gettListView().getItems()
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
							TChatMV cmv = new TChatMV(c);
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
				TChatMV mv = getModelView();
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
	}
	
	@Override
	public void setDisableModelActionButtons(boolean flag) {
		super.setDisableModelActionButtons(flag);
		if(!flag && !super.getModelView().getOwner().getValue().getId().equals(client.getOwner().getId()))
			deco.gettDeleteButton().setDisable(true);
	}
	
	@Override
	public boolean processNewEntityBeforeBuildForm(TChatMV model) {
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
	
}
