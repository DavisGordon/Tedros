package org.somos.ejb.controller;

import javax.ejb.LocalBean;
import javax.ejb.Remote;

import org.somos.model.CampanhaMailConfig;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote 
@LocalBean
public interface ICampanhaMailConfigController extends ITSecureEjbController<CampanhaMailConfig>{
	
	TResult<Boolean> processarMailing(TAccessToken token);
}
