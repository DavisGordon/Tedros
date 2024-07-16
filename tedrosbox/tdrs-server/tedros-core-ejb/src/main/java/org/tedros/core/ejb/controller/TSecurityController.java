/**
 * 
 */
package org.tedros.core.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.core.ejb.service.TSecurityService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.entity.ITUser;
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
	@Override
	public ITUser getUser(TAccessToken token) {
		return serv.getUser(token);
	}

}
