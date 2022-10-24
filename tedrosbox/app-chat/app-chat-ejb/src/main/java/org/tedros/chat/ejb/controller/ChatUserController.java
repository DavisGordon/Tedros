package org.tedros.chat.ejb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.ejb.service.ChatUserService;
import org.tedros.chat.entity.ChatUser;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="IChatUserController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.CHAT_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ChatUserController extends TSecureEjbController<ChatUser> implements	ITSecurity, IChatUserController {

	@EJB
	private ChatUserService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbService<ChatUser> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}
	
	@Override
	public TResult<ChatUser> find(TAccessToken token, ChatUser e) {
		try{
			e = serv.find(e);
			e = serv.validate(e);
			return new TResult<>(TState.SUCCESS, e);
		}catch(Exception ex){
			return processException(token, e, ex);
		}
	}
	
	@Override
	public TResult<Map<String, Object>> findAll(TAccessToken token, ChatUser entidade, int firstResult, int maxResult,
			boolean orderByAsc, boolean containsAnyKeyWords) {
		
		try{
			Number count  =  serv.countFindAll(entidade, containsAnyKeyWords);
			
			List<ChatUser> list = serv.findAll(entidade, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
			
			List<ChatUser> val = serv.validate(list);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count.longValue());
			map.put("list", val);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	
	}
	
}
