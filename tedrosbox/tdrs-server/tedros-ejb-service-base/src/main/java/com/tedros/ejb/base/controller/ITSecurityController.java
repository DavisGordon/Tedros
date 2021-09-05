/**
 * 
 */
package com.tedros.ejb.base.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ITSecurityController{

	boolean isAccessGranted(TAccessToken clent);
	
}
