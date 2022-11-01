package org.tedros.chat.ejb.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.chat.cdi.bo.ChatRoomBO;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
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
	
	@Override
	public void remove(Chat e) throws Exception {
		Chat c = new Chat();
		c.setId(e.getId());
		ChatMessage cm = new ChatMessage();
		cm.setChat(c);
		List<ChatMessage> l = msgServ.findAll(cm);
		for(ChatMessage m : l)
			msgServ.remove(m);
		super.remove(e);
	}
	
	
	public List<Chat> listAll(ChatUser c){
		return bo.listAll(c);
	}

}
