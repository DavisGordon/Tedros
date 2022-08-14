package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.core.notify.model.TNotify;
import org.tedros.server.controller.ITSecureEjbController;

@Remote
public interface TNotifyController extends ITSecureEjbController<TNotify>{

	static final String JNDI_NAME = "TNotifyControllerRemote";
	
}
