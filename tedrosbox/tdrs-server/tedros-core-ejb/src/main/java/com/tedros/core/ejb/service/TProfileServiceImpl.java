package com.tedros.core.ejb.service;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TProfileBO;
import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

@Singleton
@Stateless(name="TProfileService")
public class TProfileServiceImpl extends TEjbService<TProfile>	implements	TProfileService {

	@Inject
	private TProfileBO bo;
	
	@Override
	public ITGenericBO<TProfile> getBussinesObject() {
		return bo;
	}	
}
