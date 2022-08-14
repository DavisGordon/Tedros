/**
 * 
 */
package org.tedros.tools.module.user.process;

import org.tedros.core.security.model.TProfile;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadTProfileOptionListProcess extends TOptionsProcess<TProfile> {

	public LoadTProfileOptionListProcess() throws TProcessException {
		super(TProfile.class, "TProfileControllerRemote");
	}
}
