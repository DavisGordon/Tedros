package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TCoreService;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TPropertieController")
@TBeanSecurity(@TBeanPolicie(id="TSETTING_PROPERTIE", 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPropertieControllerImpl extends TSecureEjbController<TPropertie> implements TPropertieController, ITSecurity {

	@EJB
	private TCoreService<TPropertie> serv;
	
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
	
}
