/**
 * 
 */
package org.tedros.tools.module.user.process;

import org.tedros.core.security.model.TUser;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TEntityProcess;

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
