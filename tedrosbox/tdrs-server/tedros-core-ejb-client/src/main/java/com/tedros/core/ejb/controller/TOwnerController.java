package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface TOwnerController extends ITSecureEjbController<TOwner>{


	static final String JNDI_NAME = "TOwnerControllerRemote";
	
	TResult<TOwner> getOwner(TAccessToken token);
}
