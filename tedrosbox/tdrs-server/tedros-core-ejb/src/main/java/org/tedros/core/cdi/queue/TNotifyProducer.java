package org.tedros.core.cdi.queue;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.tedros.core.notify.model.TNotify;

@RequestScoped
public class TNotifyProducer {
	
	 @Resource(name = "jms/TedrosJmsConnectionFactory")
	 private ConnectionFactory connectionFactory;

	 @Resource(name = "NotifyQueue")
	 private Queue queue;
	 
	 public void queue(TNotify notify) {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
                        
            TextMessage message = session.createTextMessage(notify.getRefCode());
            producer.send(message);
            System.out.println("Message sent: " + notify);
        } catch (JMSException e) {
            e.printStackTrace();
        }
	 }

}
