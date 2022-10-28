/**
 * 
 */
package org.tedros.server.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.entity.ITUser;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ITSecurityController{

	public ITUser getUser(TAccessToken token);
	
	boolean isAccessGranted(TAccessToken clent);

	public boolean isPolicieAllowed(TAccessToken token, String securityId, String... action);
	
}
