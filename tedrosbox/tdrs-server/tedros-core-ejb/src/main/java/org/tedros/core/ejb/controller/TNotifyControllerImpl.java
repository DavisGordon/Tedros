package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.controller.TNotifyController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TNotifyService;
import org.tedros.core.notify.model.TNotify;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
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
	/*
	@EJB
	private TNotifyTimer timer;*/
	
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
	/*
	@Override
	public TResult<TNotify> remove(TAccessToken token, TNotify e) {
		if(e.getState()!=null && e.getState().equals(TState.SCHEDULED)) {
			timer.cancel(e);
		}
		return super.remove(token, e);
	}
	*/
	/*@Override
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
	}*/

}
