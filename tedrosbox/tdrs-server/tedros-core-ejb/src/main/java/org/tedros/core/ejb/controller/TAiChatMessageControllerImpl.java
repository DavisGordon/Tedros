/**
 * 
 */
package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.ai.model.TAiChatMessage;
import org.tedros.core.controller.TAiChatMessageController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TCoreService;
import org.tedros.core.ejb.service.TSecurityService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */

@TSecurityInterceptor
@Stateless(name="TAiChatMessageController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.CHAT_TEROS_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAiChatMessageControllerImpl extends TSecureEjbController<TAiChatMessage>  
implements TAiChatMessageController, ITSecurity {

	@EJB
	private TCoreService<TAiChatMessage> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TSecurityService securityService;
	
	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	

	@Override
	protected ITEjbService<TAiChatMessage> getService() {
		return serv;
	}
	

}
