/**
 * 
 */
package org.tedros.tools.module.user.process;

import org.tedros.core.security.model.TProfile;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TProfileProcess extends TEntityProcess<TProfile> {

	public TProfileProcess() throws TProcessException {
		super(TProfile.class, "TProfileControllerRemote");
	}

}
