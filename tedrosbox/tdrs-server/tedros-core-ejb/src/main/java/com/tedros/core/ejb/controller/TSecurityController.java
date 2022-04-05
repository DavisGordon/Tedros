/**
 * 
 */
package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TSecurityService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.security.TAccessToken;

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
	 * @see com.tedros.core.ejb.security.controller.ITSecurityController#isAccessGranted(com.tedros.ejb.base.security.TAccessToken)
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
