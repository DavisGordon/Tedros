/**
 * 
 */
package org.tedros.chat.module.client.setting;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.chat.CHATKey;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.model.Action;
import org.tedros.chat.model.ChatInfo;
import org.tedros.chat.module.client.behaviour.ChatBehaviour;
import org.tedros.chat.module.client.model.ChatMV;
import org.tedros.chat.module.client.model.ChatUserMV;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.util.TFileBaseUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITFileModel;
import org.tedros.server.model.TFileModel;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;


/**
 * @author Davis Gordon
 *
 */
public class ChatFormSetting extends TSetting {

	private ChatUtil util;
	private ChatClient client;
    private TRepository repo;
    private boolean scrollFlag = false;

	private int titleLength = 80;
	private ListChangeListener<ChatUserMV> usersChl;
    
	/**
	 * @param descriptor
	 */
	public ChatFormSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		util = new ChatUtil();
		repo = new TRepository();
		client = ChatClient.getInstance();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TSetting#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		listenReceivedMsg();
		listenSendButton();
		listenClearButton();
		listenRecipients();
		
		ChatMV mv = (ChatMV) super.getModelView();
		final ObservableList<ChatMessage> msgs = mv.getMessages();
		
		TabPane tp = super.getLayout("owner");
		// Tab title
		Tab t = tp.getTabs().get(1);
		t.textProperty().bind(mv.getTitle());
		
		// auto scroll
		ScrollPane sp = (ScrollPane) t.getContent();
		sp.setVvalue(1.0);
		sp.vvalueProperty().addListener((a,o,n)->{
			if(scrollFlag) {
				sp.setVvalue(sp.getVmax());
				if(n.doubleValue()==sp.getVmax())
					scrollFlag = false;
			}
		});
		
		verifyOwnerAsParticipant();
		
		try {
			List<ChatMessage> lst0 = util.findMessages(TedrosContext.getLoggedUser()
					.getAccessToken(),
					mv.getEntity().getId());
			
			boolean reload = false;
				
			for(ChatMessage c : lst0){
				if(c.getViewed()==null 
						|| c.getViewed().stream()
						.noneMatch(p-> p.equals(client.getOwner()))) {
					try {
						c.addViewed(client.getOwner());
						util.saveMessage(TedrosContext.getLoggedUser()
								.getAccessToken(), c);
						reload=true;
					} catch (Exception e) {
						throw new RuntimeException(e);
					};
				}
			}
			Collections.sort(lst0);
			msgs.addAll(lst0);
			mv.getMessagesLoaded().setValue(true);
			if(reload) {
				TDynaPresenter<ChatMV> p = (TDynaPresenter<ChatMV>) 
						getForm().gettPresenter();
				ChatBehaviour bhv = (ChatBehaviour) p.getBehavior();
				bhv.countUnreadMessages();
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param mv
	 */
	private void listenRecipients() {
		usersChl = new ListChangeListener<ChatUserMV>(){
			@Override
			public void onChanged(Change<? extends ChatUserMV> ch) {
				ChatMV mv = getModelView();
				List<? extends ChatUserMV> l = ch.getList();
				buildTitle(l);
				saveChat(mv);
				ChatInfo info = new ChatInfo();
				info.setAction(Action.UPDATE_RECIPIENT);
				info.setId(mv.getId().getValue());
				info.setUser(client.getOwner());
				l.forEach(u->info.addRecipient(u.getModel()));
				try {
					client.send(info);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		addRecipientsListener();
	}

	/**
	 * @param mv
	 */
	private void listenClearButton() {
		// Clear event
		EventHandler<ActionEvent> ev1 = e->{
			ChatMV mv = getModelView();
			mv.getMessage().setValue(null);
		};
		repo.add("ev1", ev1);
		TButton clearBtn = (TButton) getDescriptor()
				.getFieldDescriptor("clearBtn").getComponent();
		clearBtn.setOnAction(new WeakEventHandler<>(ev1));
	}

	/**
	 * @param mv
	 * @param msgs
	 */
	private void listenSendButton() {
		// Send event
		EventHandler<ActionEvent> ev0 = e -> {
			ChatMV mv = getModelView(); 
			final ObservableList<ChatMessage> msgs = mv.getMessages();
			this.verifyOwnerAsParticipant();
			
			ObservableList<ChatUserMV> dest = mv.getParticipants();
			if(dest.size()==0 
					|| (dest.size()==1 && dest.get(0).getEntity().getId()
					.equals(client.getOwner().getId()))) {
				Node fbx = (Node) super.getFieldBox("participants");
				PopOver ppo = new PopOver();
	        	//ppo.setHeaderAlwaysVisible(true);
	        	ppo.setAutoFix(true);
	        	ppo.setArrowLocation(ArrowLocation.RIGHT_CENTER);
	        	ppo.setContentNode(new TLabel(TLanguage.getInstance()
	        			.getString(CHATKey.MSG_SELECT_RECIPIENT)));
	        	ppo.show(fbx);
	        	return;
			}
			
			String msg = mv.getMessage().getValue();
			ITFileModel fm = mv.getSendFile().getValue();
			 try {
		            ChatMessage m = new ChatMessage();
					m.setContent(msg);
					m.setInsertDate(new Date());
					m.setFrom(client.getOwner());
					
					if(fm!=null && fm.getFile()!=null) {
						ITFileEntity fe = TFileBaseUtil.convert((TFileModel) fm);
						m.setFile((TFileEntity) fe);
					}
					
					for(ChatUserMV c : dest)
						if(!c.getId().getValue().equals(client.getOwner().getId()))
							m.addDestination(c.getEntity());
					
					m.setChat(mv.getModel());
					m = util.saveMessage(TedrosContext.getLoggedUser()
							.getAccessToken(), m);
					client.send(m);
					
					msgs.add(m);
					
					mv.getMessage().setValue(null);
					mv.getSendFile().setValue(null);
					
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		};
		repo.add("ev0", ev0);
		TButton sendBtn = (TButton) getDescriptor()
				.getFieldDescriptor("sendBtn").getComponent();
		sendBtn.setOnAction(new WeakEventHandler<>(ev0));
	}

	/**
	 * @param mv
	 * @return
	 */
	private void listenReceivedMsg() {
		ChatMV mv = getModelView();
		// Listen new messages to show
		final ObservableList<ChatMessage> msgs = mv.getMessages();
		ListChangeListener<ChatMessage> chl0 = ch0 ->{
			if(ch0.next() && ch0.wasAdded()) {
				ch0.getAddedSubList().forEach(m->{
					boolean left = !m.getFrom().getUserId()
							.equals(TedrosContext.getLoggedUser().getId());
					showMsg(m, left);
				});
			}
		};
		repo.add("chl0", chl0);
		msgs.addListener(new WeakListChangeListener<ChatMessage>(chl0));
	}

	/**
	 * @param users
	 */
	private boolean verifyOwnerAsParticipant() {
		ChatMV mv = (ChatMV) super.getModelView();
		ObservableList<ChatUserMV> users = mv.getParticipants();
		
		if(users.isEmpty() || !users.stream().filter(p->{
				return p.getEntity().equals(client.getOwner());
			}).findFirst().isPresent()) {
			users.add(new ChatUserMV(client.getOwner()));
			return true;
		}
		return false;
	}

	/**
	 * @param ch
	 */
	public void buildTitle(List<? extends ChatUserMV> ch) {
		//ChatUser owner = client.getOwner();
		ChatMV mv = (ChatMV) super.getModelView();
		String from = mv.getOwner().getValue().getName();
		StringBuilder sb = new StringBuilder(from);
		for(ChatUserMV u :  ch){
			if(!u.getId().getValue().equals(mv.getOwner().getValue().getId())) {
				if(sb.toString().equals(from)) {
					sb.append(" >> " + u.getName().getValue());
				} else 
					sb.append(", " + u.getName().getValue());
			}
		}
		String tt = sb.toString().length()>titleLength 
				? sb.toString().substring(0, titleLength-1) +"..." 
						: sb.toString();
		mv.getTitle().setValue(tt);
	}

	/**
	 * @param mv
	 */
	private void saveChat(ChatMV mv) {
		try {
			util.saveChat(TedrosContext.getLoggedUser()
					.getAccessToken(), mv.getEntity());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	private void showMsg(ChatMessage m, boolean left){
		
		scrollFlag = true;
		StackPane p1 = util.buildTextPane(m, left, open->{
			if(open) {
				TDynaPresenter<ChatMV> p = (TDynaPresenter<ChatMV>) 
						getForm().gettPresenter();
				ChatBehaviour bhv = (ChatBehaviour) p.getBehavior();
				bhv.setHidePopOver(true);
			}
		});
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane gp = super.getLayout("messages");
		int row = gp.getChildren().size();
		gp.add(p1, left ? 0 : 1, row);
		
	}
	
	@Override
	public void dispose() {
		removeRecipientsListener();
		repo.clear();
		repo = null;
		client = null;
		usersChl = null;
		ChatMV mv = getModelView();
		mv.getMessages().clear();
		mv.getMessagesLoaded().setValue(false);
	}
	/**
	 * @param mv
	 * @return
	 */
	public void addRecipientsListener() {
		ChatMV mv = super.getModelView();
		ObservableList<ChatUserMV> users = mv.getParticipants();
		users.addListener(this.usersChl);
	}
	/**
	 * @param mv
	 * @return
	 */
	public void removeRecipientsListener() {
		ChatMV mv = super.getModelView();
		ObservableList<ChatUserMV> users = mv.getParticipants();
		users.removeListener(this.usersChl);
	}

}
