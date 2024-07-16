package org.tedros.chat.ejb.controller;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.ejb.service.ChatMessageService;
import org.tedros.chat.ejb.service.ChatRoomService;
import org.tedros.chat.ejb.service.ChatUserService;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.entity.TStatus;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.ITUser;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="IChatController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.CHAT_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ChatController extends TSecureEjbController<Chat> implements	ITSecurity, IChatController {

	private ChatUser user;
	
	@EJB
	private ChatRoomService serv;
	
	@EJB
	private ChatUserService uServ;
	
	@EJB
	private ChatMessageService msgServ;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbService<Chat> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}
	
	@Override
	public TResult<Chat> remove(TAccessToken token, Chat e) {
		Chat c = new Chat();
		c.setId(e.getId());
		
		ChatMessage cm = new ChatMessage();
		cm.setChat(c);
		cm.setDateTime(null);
		try {
			List<ChatMessage> l = msgServ.findAll(cm);
			for(ChatMessage m : l)
				msgServ.remove(m);
			return super.remove(token, e);
		} catch (Exception e1) {
			return super.processException(token, e, e1);
		}
	}
	
	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")})
	public TResult<List<Chat>> listAll(TAccessToken token, Class<? extends ITEntity> entidade) {
		try {
			ChatUser c = findChatUser(token);
			List<Chat> l = serv.listAll(c);
			this.processEntityList(token, l);
			return new TResult<>(TState.SUCCESS, l);
		}catch(Exception e){
			return processException(token, null, e);
		}
	}

	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private ChatUser findChatUser(TAccessToken token) throws Exception {
		ITUser u = security.getUser(token);
		ChatUser c = new ChatUser();
		c.setUserId(u.getId());
		c = uServ.find(c);
		c = uServ.validate(c);
		return c;
	}
	
	@Override
	protected void processEntityList(TAccessToken token, List<Chat> entities) {
		user = null;
		if(entities!=null && entities.size()>0)
			entities.forEach(e->{
				this.setTotalMessages(token, e);
			});
	}
	
	@Override
	protected void processEntity(TAccessToken token, Chat entity) {
		user = null;
		if(entity!=null)
			this.setTotalMessages(token, entity);
	}

	/**
	 * @param e
	 */
	private void setTotalMessages(TAccessToken token, Chat e) {
		try {
			if(user==null) 
				user = this.findChatUser(token);
			Long total = msgServ.count(e.getId(), null, null);
			Long sent = msgServ.count(e.getId(), user.getId(), TStatus.SENT);
			Long received = msgServ.count(e.getId(), user.getId(), TStatus.RECEIVED);
			Long viewed = msgServ.count(e.getId(), user.getId(), TStatus.VIEWED);
			e.setTotalMessages(total);
			e.setTotalSentMessages(sent);
			e.setTotalReceivedMessages(received);
			e.setTotalViewedMessages(viewed);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
}
