package com.tedros.core.ejb.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TNotifyBO;
import com.tedros.core.notify.model.TNotify;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

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
