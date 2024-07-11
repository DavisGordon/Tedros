package org.tedros.core.cdi.queue;

import java.util.Date;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.tedros.core.cdi.bo.TEmailBO;
import org.tedros.core.ejb.service.TNotifyService;
import org.tedros.core.notify.model.TAction;
import org.tedros.core.notify.model.TNotify;
import org.tedros.core.notify.model.TState;
import org.tedros.server.exception.TBusinessException;

@MessageDriven(
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "NotifyQueue")
    }
)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class NotifyMDB implements MessageListener {
	
	@Inject
	private TEmailBO emailBO;
	
	@EJB
	private TNotifyService serv;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
            	TextMessage textMessage = (TextMessage) message;
                TNotify notify = new TNotify();
                notify.setRefCode(textMessage.getText());
                notify = serv.find(notify);
                processNotify(notify);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (Exception e) {
				e.printStackTrace();
			}
        } else {
            System.err.println("Message of wrong type: " + message.getClass().getName());
        }
    }

    private void processNotify(TNotify e) {
        System.out.println("Received Notify message: " + e);
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
        
        try {
			serv.save(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
    
    private void validate(TNotify e) {
		String v=null;
		if(e.getState()!=null && e.getState().equals(TState.CANCELED))
			v = "canceled";
		else if(e.getState()!=null && e.getState().equals(TState.SENT))
			v = "already sent";
		if(v!=null)
			throw new TBusinessException("This notification cant be done until it was "+v);
	}
}

