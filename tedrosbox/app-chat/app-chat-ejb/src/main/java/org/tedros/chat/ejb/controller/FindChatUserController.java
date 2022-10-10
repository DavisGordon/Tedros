package org.tedros.chat.ejb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.ejb.service.CHATService;
import org.tedros.chat.entity.ChatUser;
import org.tedros.core.security.model.TUser;
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
@Stateless(name="IFindChatUserController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.CHAT_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class FindChatUserController extends TSecureEjbController<TUser> implements	ITSecurity, IFindChatUserController {

	@EJB
	private CHATService<TUser> serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbService<TUser> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TResult<Map<String, Object>> findAll(TAccessToken token, TUser entidade, int firstResult,
			int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) {
		
		try {
			TResult<Map<String, Object>> res = super.findAll(token, entidade, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
			if(res.getState().equals(TState.SUCCESS)) {
				List<TUser> l = (List<TUser>) res.getValue().get("list");
				List<ChatUser> chatUsers = new ArrayList<>();
				l.forEach(u->{
					StringBuilder sb = new StringBuilder();
					u.getProfiles().forEach(p->{
						sb.append((sb.toString().isEmpty() ? "" : ", ")+ p.getName());
					});
					chatUsers.add(new ChatUser(u.getId(), u.getName(), sb.toString()));
				});
				res.getValue().put("list", chatUsers);
			}
			return res;
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}
	
}
