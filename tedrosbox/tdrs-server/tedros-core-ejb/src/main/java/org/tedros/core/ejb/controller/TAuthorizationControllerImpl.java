package org.tedros.core.ejb.controller;

import java.util.List;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.core.controller.TAuthorizationController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TAuthorizationService;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TAuthorizationController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.AUTHORIZATION_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAuthorizationControllerImpl extends TSecureEjbController<TAuthorization> implements TAuthorizationController, ITSecurity {

	@EJB
	private TAuthorizationService serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TAuthorization> getService() {
		return serv;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	@TMethodSecurity(@TMethodPolicie(policie={TActionPolicie.NEW}))
	public TResult process(TAccessToken token, List<TAuthorization> newLst) {
		try{
			List<TAuthorization> oldLst = serv.listAll(TAuthorization.class);
			List<String> msg = serv.process(newLst, oldLst);
			return new TResult<List<String>>(TState.SUCCESS, msg);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult(TState.ERROR, e.getMessage());
		}
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	
}
