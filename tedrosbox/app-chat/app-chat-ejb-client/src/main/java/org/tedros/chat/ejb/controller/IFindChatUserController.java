package org.tedros.chat.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.core.security.model.TUser;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface IFindChatUserController extends ITSecureEjbController<TUser>{
	
	static final String JNDI_NAME = "IFindChatUserControllerRemote";
	
}
