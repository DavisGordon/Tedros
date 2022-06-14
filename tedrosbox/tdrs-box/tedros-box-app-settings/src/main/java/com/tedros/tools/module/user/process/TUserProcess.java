/**
 * 
 */
package com.tedros.tools.module.user.process;

import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TUserProcess extends TEntityProcess<TUser> {
	
	private static final String SERV_NAME = "TUserControllerRemote";
	
	public TUserProcess() throws TProcessException {
		super(TUser.class, SERV_NAME);
	} 

}
