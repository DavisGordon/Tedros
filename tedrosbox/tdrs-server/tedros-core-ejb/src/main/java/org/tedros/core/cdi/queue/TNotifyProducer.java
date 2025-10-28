package org.tedros.core.cdi.queue;

import org.tedros.core.notify.model.TNotify;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

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
