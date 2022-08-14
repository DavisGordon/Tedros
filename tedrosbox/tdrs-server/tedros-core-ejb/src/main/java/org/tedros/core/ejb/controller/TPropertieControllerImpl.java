package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.ejb.service.TPropertieService;
import org.tedros.core.ejb.timer.TNotifyTimer;
import org.tedros.core.setting.model.TPropertie;
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
@Stateless(name="TPropertieController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.PROPERTIE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPropertieControllerImpl extends TSecureEjbController<TPropertie> implements TPropertieController, ITSecurity {

	@EJB
	private TPropertieService serv;
	
	@EJB
	private TNotifyTimer timer;
	
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
	public TResult<TPropertie> save(TAccessToken token, TPropertie e) {

		if(e.getKey().equals(TSystemPropertie.NOTIFY_INTERVAL_TIMER.getValue()))
			if(e.getValue()!=null && NumberUtils.isCreatable(e.getValue())) {
				timer.start(e.getValue());
			}else
				timer.stop();
		
		return super.save(token, e);
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
