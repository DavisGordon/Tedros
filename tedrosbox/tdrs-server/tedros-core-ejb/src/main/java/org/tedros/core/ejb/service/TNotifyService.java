package org.tedros.core.ejb.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.cdi.bo.TNotifyBO;
import org.tedros.core.cdi.queue.TNotifyProducer;
import org.tedros.core.notify.model.TNotify;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@LocalBean
@Stateless(name="TNotifyService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TNotifyService extends TEjbService<TNotify> {

	@Inject
	private TNotifyBO bo;
	
	@Inject
	private TNotifyProducer sender;
	
	@Override
	public ITGenericBO<TNotify> getBussinesObject() {
		return bo;
	}
		
	public List<TNotify> listToProcess(){
		return bo.listToProcess();
	}
	
	public void queue(TNotify notify) {
		sender.queue(notify);
	}
	

}
