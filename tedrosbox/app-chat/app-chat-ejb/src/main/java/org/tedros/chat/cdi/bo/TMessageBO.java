package org.tedros.chat.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.chat.cdi.eao.TMessageEao;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.server.cdi.bo.TGenericBO;

@RequestScoped
public class TMessageBO extends TGenericBO<ChatMessage> {
	
	@Inject
	private TMessageEao eao;
	
	@Override
	public TMessageEao getEao() {
		return eao;
	}


}
