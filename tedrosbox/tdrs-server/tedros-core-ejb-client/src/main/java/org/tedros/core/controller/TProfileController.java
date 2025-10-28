package org.tedros.core.controller;

import jakarta.ejb.Remote;

import org.tedros.core.security.model.TProfile;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface TProfileController extends ITSecureEjbController<TProfile>{


	static final String JNDI_NAME = "TProfileControllerRemote";
}
