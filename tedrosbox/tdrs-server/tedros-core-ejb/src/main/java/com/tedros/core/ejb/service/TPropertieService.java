package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TCoreBO;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

@Local
@Stateless(name="TPropertieService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPropertieService extends TEjbService<TPropertie>	{

	@Inject
	private TCoreBO<TPropertie> bo;
	
	@Override
	public ITGenericBO<TPropertie> getBussinesObject() {
		return bo;
	}

}
