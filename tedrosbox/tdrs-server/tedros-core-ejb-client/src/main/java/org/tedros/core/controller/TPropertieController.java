package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

@Remote
public interface TPropertieController extends ITSecureEjbController<TPropertie>{

	static final String JNDI_NAME = "TPropertieControllerRemote";
	
	TResult<String> getValue(TAccessToken token, String key);
	
	TResult<TFileEntity> getFile(TAccessToken token, String key);
}
