package org.tedros.core.ejb.service;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.cdi.bo.TMessageBO;
import org.tedros.core.message.model.TMessage;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@Local
@Stateless(name="TMessageService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TMessageService extends TEjbService<TMessage> {

	@Inject
	private TMessageBO bo;
	
	@EJB
	private TSecurityService serv;
	
	@Override
	public ITGenericBO<TMessage> getBussinesObject() {
		return bo;
	}

}
