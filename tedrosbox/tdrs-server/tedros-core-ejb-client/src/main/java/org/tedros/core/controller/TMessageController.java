package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.core.message.model.TMessage;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface TMessageController extends ITSecureEjbController<TMessage>{
	

	static final String JNDI_NAME = "TMessageControllerRemote";
}
