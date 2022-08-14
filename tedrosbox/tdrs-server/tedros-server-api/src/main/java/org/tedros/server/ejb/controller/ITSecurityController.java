/**
 * 
 */
package org.tedros.server.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ITSecurityController{

	boolean isAccessGranted(TAccessToken clent);

	public boolean isPolicieAllowed(TAccessToken token, String securityId, String... action);
	
}
