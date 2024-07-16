package org.tedros.core.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.core.controller.TNotifyController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TNotifyService;
import org.tedros.core.notify.model.TAction;
import org.tedros.core.notify.model.TNotify;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TNotifyController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.NOTIFY_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TNotifyControllerImpl extends TSecureEjbController<TNotify> implements TNotifyController, ITSecurity {

	@EJB
	private TNotifyService serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<TNotify> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	@Override
	public TResult<TNotify> save(TAccessToken token, TNotify e) {
		
		try {
			e = serv.save(e);
			
			if(e.getAction().equals(TAction.SEND))
				serv.queue(e);
			
			return new TResult<>(TState.SUCCESS, e);
		}catch(Exception ex){
			return processException(token, e, ex);
		}
	}

}
