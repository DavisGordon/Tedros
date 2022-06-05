package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface TPropertieController extends ITSecureEjbController<TPropertie>{

	static final String JNDI_NAME = "TPropertieControllerRemote";
	
	TResult<String> getValue(TAccessToken token, String key);
	
	TResult<TFileEntity> getFile(TAccessToken token, String key);
}
