package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface TUserController extends ITSecureEjbController<TUser>{
	

	static final String JNDI_NAME = "TUserControllerRemote";
}
