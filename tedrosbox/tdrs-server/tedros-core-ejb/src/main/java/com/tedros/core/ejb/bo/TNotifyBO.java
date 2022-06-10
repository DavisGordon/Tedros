package com.tedros.core.ejb.bo;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.core.ejb.eao.TNotifyEao;
import com.tedros.core.notify.model.TAction;
import com.tedros.core.notify.model.TNotify;
import com.tedros.core.notify.model.TState;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.exception.TBusinessException;

@RequestScoped
public class TNotifyBO extends TGenericBO<TNotify> {
	
	@Inject
	private EmailBO emailBO;
	
	@Inject
	private TNotifyEao eao;
	
	@Override
	public TNotifyEao getEao() {
		return eao;
	}
	
	public void process(TNotify e) {
		try {
			validate(e);
			if(e.getFile()!=null) 
				emailBO.send(false, e.getTo(), e.getSubject(), e.getContent(), true, e.getFile());
			else
				emailBO.send(false, e.getTo(), e.getSubject(), e.getContent(), true);
	
			e.setAction(TAction.NONE);
			e.setState(TState.SENT);
			e.setProcessedTime(new Date());
			e.addEventLog(TState.SENT, null);
			
		} catch (Throwable e1) {
			e1.printStackTrace();
			e.setAction(TAction.NONE);
			e.setState(TState.ERROR);
			e.setProcessedTime(new Date());
			e.addEventLog(TState.ERROR, e1.getMessage());
		}
	}
	
	public List<TNotify> process()  {
		List<TNotify> l = eao.listToProcess();
		if(l!=null && !l.isEmpty())
			for(TNotify e : l) 
				process(e);
		return l;
	}
	
	public TNotify process(String refCode) throws Exception {
		if(refCode!=null) {
			TNotify ex = new TNotify();
			ex.setRefCode(refCode);
			ex = find(ex);
			if(ex!=null) {
				process(ex);
				return ex;
			}else
				throw new TBusinessException("#{tedros.fxapi.message.no.data.found}");
		}else
			throw new IllegalArgumentException("The argument cannot be null");
	}


	@SuppressWarnings("incomplete-switch")
	private void validate(TNotify e) {
		String v=null;
		if(e.getState()!=null)
			switch(e.getState()) {
			case CANCELED:
				v = "canceled";
				break;
			case SENT:
				v = "already sent";
				break;
			}
		if(v!=null)
			throw new TBusinessException("This notification cant be done until it was "+v);
	}
	
}
