package org.tedros.chat.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.chat.cdi.bo.TMessageBO;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@Local
@Stateless(name="TMessageService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TMessageService extends TEjbService<ChatMessage> {

	@Inject
	private TMessageBO bo;
	
	@Override
	public ITGenericBO<ChatMessage> getBussinesObject() {
		return bo;
	}

}
