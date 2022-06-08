/**
 * 
 */
package com.tedros.core.ejb.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.tedros.core.domain.DomainPropertie;
import com.tedros.core.domain.TSystemPropertie;
import com.tedros.core.ejb.bo.TPropertieBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ResourceProducer {
	
	@Inject
	private TPropertieBO propBO;
	
	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_HOST)
	public String getSmtpHost(){
		return propBO.getValue(TSystemPropertie.SMTP_HOST.getValue());
	}
	
	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_USER)
	public String getSmtpUser(){
		return propBO.getValue(TSystemPropertie.SMTP_USER.getValue());
	}
	
	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_PASS)
	public String getSmtpPass(){
		return propBO.getValue(TSystemPropertie.SMTP_PASS.getValue());
	}
	

	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_PORT)
	public String getSmtpPort(){
		return propBO.getValue(TSystemPropertie.SMTP_PORT.getValue());
	}
	

	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_SOCKET_PORT)
	public String getSmtpSocketPort(){
		return propBO.getValue(TSystemPropertie.SMTP_SOCKET_PORT.getValue());
	}

}
