package org.tedros.chat.ejb.service;

import java.util.List;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.chat.cdi.bo.ChatUserBO;
import org.tedros.chat.entity.ChatUser;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@Local
@Stateless(name="ChatUserService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ChatUserService extends TEjbService<ChatUser> {

	@Inject
	private ChatUserBO bo;
	
	@Override
	public ITGenericBO<ChatUser> getBussinesObject() {
		return bo;
	}
	
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public List<ChatUser> validate(List<ChatUser> lst) throws Exception {
		return bo.validate(lst);
	}
	

	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public ChatUser validate(ChatUser e) throws Exception {
		return bo.validate(e);
	}
		

}
