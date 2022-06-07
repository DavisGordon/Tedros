package com.tedros.util;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public final class TSMTPUtil {
	
	private static TSMTPUtil instance;
	
	private final Properties props;
	
	private final Session session;
	/*
	public static void main(String[] args) {
		try {
			TSMTPUtil.getInstance("smtp.gmail.com", "587", "javax.net.ssl.SSLSocketFactory", 
					"true", "587", "@gmail.com", "")
			.sent(true, "@gmail.com", ".@gmail.com", "subject", "content", true);
		} catch (TSentEmailException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public static TSMTPUtil getInstance(String smtpHost, String smtpSocketPort, String smtpSocketClass, 
			String smtpAuth, String smtpPort, String userName, String password){
		
		if(instance==null)
			instance = new TSMTPUtil(smtpHost, smtpSocketPort, smtpSocketClass, smtpAuth, smtpPort, userName, password);
		
		return instance;
	}
	
	private TSMTPUtil(String smtpHost, String smtpSocketPort, String smtpSocketClass, 
			String smtpAuth, String smtpPort, final String userName, final String password){
		
		props = new Properties();
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
	

	public void send(boolean debug, String from, String to, String subject, String content, boolean html, 
			String attachFile, byte[] attach) throws TSentEmailException {
		// enable/disable debug
	    session.setDebug(debug);
	    try {
	    	Message message = new MimeMessage(session);
	    	message.setFrom(new InternetAddress(from)); 
	    	Address[] toUser = InternetAddress.parse(to); 
	    	message.setRecipients(Message.RecipientType.TO, toUser);
	    	message.setSubject(subject);
	    	
	    	if(html)
	    		message.setContent(content, "text/html; charset=utf-8");
	    	else
	    		message.setText(content);
	    	
	    	if(attach!=null && attachFile!=null) {
		    	ByteArrayDataSource bds = new ByteArrayDataSource(attach, attachFile); 
		    	message.setDataHandler(new DataHandler(bds)); 
		    	message.setFileName(bds.getName()); 
	    	}
	    	Transport.send(message);
	    } catch (MessagingException e) {
	    		throw new TSentEmailException(e);
	    }
	}
	
	public void send(boolean debug, String from, String to, String subject, String content, boolean html) throws TSentEmailException {
		// enable/disable debug
	    session.setDebug(debug);
	    try {
	    	Message message = new MimeMessage(session);
	    	message.setFrom(new InternetAddress(from)); 
	    	Address[] toUser = InternetAddress.parse(to); 
	    	message.setRecipients(Message.RecipientType.TO, toUser);
	    	message.setSubject(subject);
	    	
	    	if(html)
	    		message.setContent(content, "text/html; charset=utf-8");
	    	else
	    		message.setText(content);
	    	
	    	Transport.send(message);
	    } catch (MessagingException e) {
	    		throw new TSentEmailException(e);
	    }
	}
}
