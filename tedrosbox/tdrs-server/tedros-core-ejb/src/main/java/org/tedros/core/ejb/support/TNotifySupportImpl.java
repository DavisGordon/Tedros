package org.tedros.core.ejb.support;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.ejb.service.TNotifyService;
import org.tedros.core.notify.model.TAction;
import org.tedros.core.notify.model.TNotify;
import org.tedros.core.notify.model.TState;
import org.tedros.core.support.TNotifySupport;
import org.tedros.server.exception.TBusinessException;

@Stateless(name="TNotifySupport")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TNotifySupportImpl implements TNotifySupport {

	@EJB
	private TNotifyService serv;
		
	public TState getState(String refCode) throws Exception {
		TNotify n = new TNotify();
		n.setRefCode(refCode);
		try {
			n = serv.find(n);
			return n.getState();
		} catch (Exception e) {
			throw new TBusinessException("#{tedros.fxapi.message.no.data.found}");
		}
	}

	public void cancel(String refCode) throws Exception {
		TNotify n = new TNotify();
		n.setRefCode(refCode);
		try {
			n = serv.find(n);
			n.setAction(TAction.CANCEL);
			serv.save(n);
		} catch (Exception e) {
			throw new TBusinessException("#{tedros.fxapi.message.no.data.found}");
		}
	}

	public String send(String emailTo, String subject, String content) throws Exception {
		TNotify n = new TNotify();
		n.setTo(emailTo);
		n.setSubject(subject);
		n.setContent(content);
		n.setAction(TAction.SEND);
		
		n = serv.save(n);
		return n.getRefCode();
	}
	
	public String toQueue(String emailTo, String subject, String content) throws Exception {
		TNotify n = new TNotify();
		n.setTo(emailTo);
		n.setSubject(subject);
		n.setContent(content);
		n.setAction(TAction.TO_QUEUE);
		
		n = serv.save(n);
		return n.getRefCode();
	}
	
	public String toSchedule(String emailTo, String subject, String content, Date dateTime) throws Exception {
		TNotify n = new TNotify();
		n.setTo(emailTo);
		n.setSubject(subject);
		n.setContent(content);
		n.setScheduleTime(dateTime);
		n.setAction(TAction.TO_SCHEDULE);
		
		n = serv.save(n);
		return n.getRefCode();
	}

}
