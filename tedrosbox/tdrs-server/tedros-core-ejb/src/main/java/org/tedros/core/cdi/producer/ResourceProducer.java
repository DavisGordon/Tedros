/**
 * 
 */
package org.tedros.core.cdi.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.tedros.core.cdi.bo.TPropertieBO;

import org.tedros.core.domain.DomainPropertie;
import org.tedros.core.domain.TSystemPropertie;

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
	public Item<String> getSmtpHost(){
		return new Item<>(propBO.getValue(TSystemPropertie.SMTP_HOST.getValue()));
	}
	
	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_USER)
	public Item<String> getSmtpUser(){
		return new Item<>(propBO.getValue(TSystemPropertie.SMTP_USER.getValue()));
	}
	
	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_PASS)
	public Item<String> getSmtpPass(){
		return new Item<>(propBO.getValue(TSystemPropertie.SMTP_PASS.getValue()));
	}
	

	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_PORT)
	public Item<String> getSmtpPort(){
		return new Item<>(propBO.getValue(TSystemPropertie.SMTP_PORT.getValue()));
	}
	

	@Produces
	@RequestScoped
	@Named(DomainPropertie.SMTP_SOCKET_PORT)
	public Item<String> getSmtpSocketPort(){
		return new Item<>(propBO.getValue(TSystemPropertie.SMTP_SOCKET_PORT.getValue()));
	}
	
	@Produces
	@RequestScoped
	@Named(DomainPropertie.NOTIFY_INTERVAL_TIMER)
	public Item<String> getNotifyInterval(){
		return new Item<>(propBO.getValue(TSystemPropertie.NOTIFY_INTERVAL_TIMER.getValue()));
	}

}
