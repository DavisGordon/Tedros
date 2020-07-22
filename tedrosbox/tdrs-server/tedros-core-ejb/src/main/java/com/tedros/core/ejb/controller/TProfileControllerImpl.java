package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tedros.core.ejb.service.TProfileServiceImpl;
import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;


@Stateless(name="TProfileController")
public class TProfileControllerImpl extends TEjbController<TProfile>	implements	TProfileController {

	@EJB
	private TProfileServiceImpl serv;

	@Override
	public ITEjbService<TProfile> getService() {
		return serv;
	}
	
}