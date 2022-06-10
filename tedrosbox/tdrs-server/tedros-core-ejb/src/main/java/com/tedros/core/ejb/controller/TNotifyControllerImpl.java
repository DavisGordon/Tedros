package com.tedros.core.ejb.controller;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.domain.DomainApp;
import com.tedros.core.ejb.service.TNotifyService;
import com.tedros.core.ejb.timer.TNotifyTimer;
import com.tedros.core.notify.model.TAction;
import com.tedros.core.notify.model.TNotify;
import com.tedros.core.notify.model.TState;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TNotifyController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.PROPERTIE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TNotifyControllerImpl extends TSecureEjbController<TNotify> implements TNotifyController, ITSecurity {

	@EJB
	private TNotifyService serv;
	
	@EJB
	private TNotifyTimer timer;
	
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
	public TResult<TNotify> remove(TAccessToken token, TNotify e) {
		if(e.getState().equals(TState.SCHEDULED)) {
			timer.cancel(e);
		}
		return super.remove(token, e);
	}
	
	@Override
	public TResult<TNotify> save(TAccessToken token, TNotify e) {
		
		switch(e.getAction()) {
		case CANCEL:
			if(e.getState().equals(TState.SCHEDULED)) {
				timer.cancel(e);
			}
			e.setState(TState.CANCELED);
			e.setProcessedTime(new Date());
			e.setAction(TAction.NONE);
			e.addEventLog(TState.CANCELED, null);
			break;
		case SEND:
			e.setState(null);
			serv.process(e);
			break;
		case TO_QUEUE:
			e.setState(TState.QUEUED);
			e.setProcessedTime(new Date());
			e.setAction(TAction.NONE);
			e.addEventLog(TState.QUEUED, null);
			break;
		case TO_SCHEDULE:
			if(e.getScheduleTime()!=null) {
				Date sch = e.getScheduleTime();
				if(Calendar.getInstance().after(sch)) {
					return new TResult<>(TResult.TState.WARNING, true, "#{tedros.fxapi.validator.future.date}");
				}
				timer.schedule(e);
				e.setState(TState.SCHEDULED);
				e.setProcessedTime(new Date());
				e.setAction(TAction.NONE);
				e.addEventLog(TState.SCHEDULED, null);
			}else
				return new TResult<>(TResult.TState.WARNING, true, "#{tedros.fxapi.validator.future.date}");
			break;
		default:
			break;
		}
		
		return super.save(token, e);
	}

}
