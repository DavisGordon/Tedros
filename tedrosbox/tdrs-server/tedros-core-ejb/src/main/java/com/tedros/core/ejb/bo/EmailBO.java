/**
 * 
 */
package com.tedros.core.ejb.bo;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.domain.DomainPropertie;
import com.tedros.core.ejb.producer.Item;
import com.tedros.util.TSMTPUtil;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmailBO {
	
	@Inject
	@Named(DomainPropertie.SMTP_HOST)
	private Item<String> smtpHost;

	@Inject
	@Named(DomainPropertie.SMTP_PORT)
	private Item<String> smtpPort;

	@Inject
	@Named(DomainPropertie.SMTP_SOCKET_PORT)
	private Item<String> socketPort;
	
	@Inject
	@Named(DomainPropertie.SMTP_USER)
	private Item<String> emailAccount;
	
	@Inject
	@Named(DomainPropertie.SMTP_PASS)
	private Item<String> passAccount;
	
	private TSMTPUtil util;
	
	@PostConstruct
	public void init(){
		util = TSMTPUtil.getInstance(smtpHost.get(), socketPort.get(), "javax.net.ssl.SSLSocketFactory", 
				"true", smtpPort.get(), emailAccount.get(), passAccount.get());
	}
	
	public void send(boolean debug, String to, String subject, String content, boolean html) throws TSentEmailException{
		util.send(debug, emailAccount.get(), to, subject, content, html);
	}
	

	public void send(boolean debug, String to, String subject, String content, boolean html, TFileEntity file) throws TSentEmailException{
		util.send(debug, emailAccount.get(), to, subject, content, html, file.getFileName(), file.getByte().getBytes());
	}
	
	
}
