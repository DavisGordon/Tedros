package com.tedros.util;


import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TEmailUtil {
	
	private static TEmailUtil instance;
	
	private final Properties props;
	
	private final Session session;
	
	public static void main(String[] args) {
		TEmailUtil.getInstance("smtp.gmail.com", "587", "javax.net.ssl.SSLSocketFactory", 
				"true", "587", "tedrosbox@gmail.com", "$tdrs#221978@")
		.sent(true, "tedrosbox@gmail.com", "davis.dun@gmail.com", "subject", "content", true);
	}
	
	public static TEmailUtil getInstance(String smtpHost, String smtpSocketPort, String smtpSocketClass, 
			String smtpAuth, String smtpPort, String userName, String password){
		
		if(instance==null)
			instance = new TEmailUtil(smtpHost, smtpSocketPort, smtpSocketClass, smtpAuth, smtpPort, userName, password);
		
		return instance;
	}
	
	private TEmailUtil(String smtpHost, String smtpSocketPort, String smtpSocketClass, 
			String smtpAuth, String smtpPort, final String userName, final String password){
		props = new Properties();
	    /** Parâmetros de conexão com servidor Gmail */
	    props.put("mail.smtp.host", smtpHost);
	    props.put("mail.smtp.socketFactory.port", smtpSocketPort);
	    props.put("mail.smtp.socketFactory.class", smtpSocketClass);
	    props.put("mail.smtp.auth", smtpAuth);
	    props.put("mail.smtp.port", smtpPort);
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.class", "com.sun.mail.smtp.SMTPTransport");
	    // props.put("mail.smtp.ssl.enable", "true");
	    session = Session.getDefaultInstance(props,
	  	      new javax.mail.Authenticator() {
	  	           protected PasswordAuthentication getPasswordAuthentication() 
	  	           {
	  	                 return new PasswordAuthentication(userName, password);
	  	           }
	  	      });
	  	    
	}
	
	  public void sent(boolean debug, String from, String to, String subject, String content, boolean html) {
	   
	 
	    /** Ativa Debug para sessão */
	    session.setDebug(debug);
	 
	    try {
	 
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(from)); 
	      //Remetente
	    //Destinatário(s)
	      Address[] toUser = InternetAddress.parse(to);  
	 
	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject(subject);//Assunto
	      
	      if(html) 
	    	  message.setContent(content, "text/html; charset=utf-8");
	      else
	    	  message.setText(content);
	      
	      /**Método para enviar a mensagem criada*/
	      Transport.send(message);
	 
	     } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	  }
	}