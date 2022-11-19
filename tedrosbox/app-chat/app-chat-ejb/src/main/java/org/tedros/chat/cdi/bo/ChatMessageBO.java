package org.tedros.chat.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.chat.cdi.eao.ChatMessageEao;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.TStatus;
import org.tedros.server.cdi.bo.TGenericBO;

@RequestScoped
public class ChatMessageBO extends TGenericBO<ChatMessage> {
	
	@Inject
	private ChatMessageEao eao;
	
	@Override
	public ChatMessageEao getEao() {
		return eao;
	}
	

	public Long count(Long chatId, Long userId, TStatus status){
		return eao.count(chatId, userId, status);
	}


}
