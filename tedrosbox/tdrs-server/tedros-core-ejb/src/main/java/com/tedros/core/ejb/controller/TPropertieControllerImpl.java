package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.service.TPropertieService;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TPropertieController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.PROPERTIE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPropertieControllerImpl extends TSecureEjbController<TPropertie> implements TPropertieController, ITSecurity {

	@EJB
	private TPropertieService serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TPropertie> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<String> getValue(TAccessToken token, String key) {
		try {
			String v = serv.getValue(key);
			return new TResult<>(TState.SUCCESS, "", v);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}

	@Override
	public TResult<TFileEntity> getFile(TAccessToken token, String key) {
		try {
			TFileEntity v = serv.getFile(key);
			return new TResult<>(TState.SUCCESS, v);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}

	
}
