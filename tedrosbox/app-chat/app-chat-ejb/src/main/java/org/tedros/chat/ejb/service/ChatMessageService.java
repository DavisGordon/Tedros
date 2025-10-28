package org.tedros.chat.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.chat.cdi.bo.ChatMessageBO;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.TStatus;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@Local
@Stateless(name="ChatMessageService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ChatMessageService extends TEjbService<ChatMessage> {

	@Inject
	private ChatMessageBO bo;
	
	@Override
	public ITGenericBO<ChatMessage> getBussinesObject() {
		return bo;
	}
	
	public Long count(Long chatId, Long userId, TStatus status){
		return bo.count(chatId, userId, status);
	}

}
