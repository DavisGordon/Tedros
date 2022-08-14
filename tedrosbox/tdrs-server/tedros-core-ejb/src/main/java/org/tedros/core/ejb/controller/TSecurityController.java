/**
 * 
 */
package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.ejb.service.TSecurityService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="ITSecurityController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TSecurityController implements ITSecurityController {
	
	@EJB
	private TSecurityService serv;
	
	/* (non-Javadoc)
	 * @see org.tedros.core.ejb.security.controller.ITSecurityController#isAccessGranted(org.tedros.server.ejb.security.TAccessToken)
	 */
	@Override
	public boolean isAccessGranted(TAccessToken e) {
		return serv.isAssigned(e);
	}
	@Override
	public boolean isPolicieAllowed(TAccessToken token, String securityId, String... action) {
		return serv.isActionGranted(token, securityId, action);
	}

}
