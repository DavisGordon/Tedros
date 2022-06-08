package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.notify.model.TNotify;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface TNotifyController extends ITSecureEjbController<TNotify>{

	static final String JNDI_NAME = "TNotifyControllerRemote";
	
}
