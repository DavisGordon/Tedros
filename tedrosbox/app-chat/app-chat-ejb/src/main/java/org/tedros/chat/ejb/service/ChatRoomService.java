package org.tedros.chat.ejb.service;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.chat.cdi.bo.ChatRoomBO;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatUser;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@Local
@Stateless(name="ChatRoomService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ChatRoomService extends TEjbService<Chat> {

	@Inject
	private ChatRoomBO bo;
	
	@EJB
	private ChatMessageService msgServ;
	
	@Override
	public ITGenericBO<Chat> getBussinesObject() {
		return bo;
	}
	
	public List<Chat> listAll(ChatUser c){
		return bo.listAll(c);
	}

}
