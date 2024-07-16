package org.tedros.core.controller;

import jakarta.ejb.Remote;

import org.tedros.core.security.model.TUser;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface TUserController extends ITSecureEjbController<TUser>{
	

	static final String JNDI_NAME = "TUserControllerRemote";
}
