/**
 * 
 */
package com.tedros.tools.module.user.process;

import com.tedros.core.security.model.TProfile;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadTProfileOptionListProcess extends TOptionsProcess<TProfile> {

	public LoadTProfileOptionListProcess() throws TProcessException {
		super(TProfile.class, "TProfileControllerRemote");
	}
}
