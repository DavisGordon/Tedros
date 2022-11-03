package org.tedros.chat.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.ejb.service.ChatMessageService;
import org.tedros.chat.ejb.service.ChatRoomService;
import org.tedros.chat.ejb.service.ChatUserService;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
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
			ITUser u = security.getUser(token);
			ChatUser c = new ChatUser();
			c.setUserId(u.getId());
			c = uServ.find(c);
			c = uServ.validate(c);
			List<Chat> l = serv.listAll(c);
			return new TResult<>(TState.SUCCESS, l);
		}catch(Exception e){
			return processException(token, null, e);
		}
	}
	
	
}
