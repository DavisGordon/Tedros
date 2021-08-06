package com.covidsemfome.ejb.mdb;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


//@MessageDriven
public class SiteVisitorBean implements MessageListener {

	/*@Resource(name = "siteVisitorQueue")
    private Queue queue;*/

    public void onMessage(Message message) {
        try {

            final TextMessage textMessage = (TextMessage) message;
            final String question = textMessage.getText();

            System.out.println(question);
           
        } catch (JMSException e) {
            throw new IllegalStateException(e);
        }
    }

}
