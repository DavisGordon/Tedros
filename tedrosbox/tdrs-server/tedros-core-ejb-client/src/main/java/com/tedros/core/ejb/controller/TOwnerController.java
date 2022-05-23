package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface TOwnerController extends ITSecureEjbController<TOwner>{


	static final String JNDI_NAME = "TOwnerControllerRemote";
}
