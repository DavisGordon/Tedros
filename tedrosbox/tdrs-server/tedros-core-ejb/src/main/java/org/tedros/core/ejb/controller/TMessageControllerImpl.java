package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.controller.TMessageController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TMessageService;
import org.tedros.core.message.model.TMessage;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TMessageController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.MESSAGE_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TMessageControllerImpl extends TSecureEjbController<TMessage> implements	ITSecurity, TMessageController {

	@EJB
	private TMessageService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbService<TMessage> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}
	
	
}
