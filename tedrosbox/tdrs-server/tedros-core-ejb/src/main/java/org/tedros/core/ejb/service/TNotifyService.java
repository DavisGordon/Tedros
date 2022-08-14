package org.tedros.core.ejb.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.cdi.bo.TNotifyBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import org.tedros.core.notify.model.TNotify;

@LocalBean
@Stateless(name="TNotifyService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TNotifyService extends TEjbService<TNotify>	{

	@Inject
	private TNotifyBO bo;
	
	@Override
	public ITGenericBO<TNotify> getBussinesObject() {
		return bo;
	}
	
	public void process(TNotify e) {
		bo.process(e);
	}
	
	public TNotify process(String refCode) throws Exception {
		return bo.process(refCode);
	}
	
	public List<TNotify> process() {
		return bo.process();
	}
	

}
