package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TProfileBO;
import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

@Local
@Stateless(name="TProfileService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TProfileService extends TEjbService<TProfile>	{

	@Inject
	private TProfileBO bo;
	
	@Override
	public ITGenericBO<TProfile> getBussinesObject() {
		return bo;
	}	
}
