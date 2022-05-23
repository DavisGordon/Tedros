package com.tedros.core.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.tedros.core.security.model.TAuthorization;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface TAuthorizationController extends ITSecureEjbController<TAuthorization>{

	static final String JNDI_NAME = "TAuthorizationControllerRemote";
	
	@SuppressWarnings("rawtypes")
	TResult process(TAccessToken token, List<TAuthorization> newLst);

	
}
