package org.tedros.core.cdi.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.core.cdi.eao.TNotifyEao;
import org.tedros.core.ejb.timer.TNotifyTimer;
import org.tedros.core.notify.model.TAction;
import org.tedros.core.notify.model.TNotify;
import org.tedros.core.notify.model.TState;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.exception.TBusinessException;

@RequestScoped
public class TNotifyBO extends TGenericBO<TNotify> {
	
	@EJB
	private TNotifyTimer timer;
	
	@Inject
	private TNotifyEao eao;
	
	@Override
	public TNotifyEao getEao() {
		return eao;
	}
	
	public List<TNotify> listToProcess(){
		return eao.listToProcess();
	}

	@Override
	public void remove(TNotify e) throws Exception {
		if(e.getState()!=null && e.getState().equals(TState.SCHEDULED)) {
			timer.cancel(e);
		}
		super.remove(e);
	}
	
	@Override
	public TNotify save(TNotify e) throws Exception {
		
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
					throw new TBusinessException(true, "#{tedros.fxapi.validator.future.date}");
				}
				timer.schedule(e);
				e.setState(TState.SCHEDULED);
				e.setProcessedTime(new Date());
				e.setAction(TAction.NONE);
				e.addEventLog(TState.SCHEDULED, null);
			}else
				throw new TBusinessException(true, "#{tedros.fxapi.validator.future.date}");
			break;
		default:
			break;
		}
		
		return super.save(e);
	}
	
	/*
	 * public void process(TNotify e) { try { validate(e); if(e.getFile()!=null)
	 * emailBO.send(false, e.getTo(), e.getSubject(), e.getContent(), true,
	 * e.getFile()); else emailBO.send(false, e.getTo(), e.getSubject(),
	 * e.getContent(), true);
	 * 
	 * e.setAction(TAction.NONE); e.setState(TState.SENT); e.setProcessedTime(new
	 * Date()); e.addEventLog(TState.SENT, null);
	 * 
	 * } catch (Throwable e1) { e1.printStackTrace(); e.setAction(TAction.NONE);
	 * e.setState(TState.ERROR); e.setProcessedTime(new Date());
	 * e.addEventLog(TState.ERROR, e1.getMessage()); } }
	 */
	
	
	
	
	
	
}
