/**
 * 
 */
package com.tedros.settings.properties.process;

import com.tedros.core.security.model.TProfile;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TProfileProcess extends TEntityProcess<TProfile> {

	public TProfileProcess() throws TProcessException {
		super(TProfile.class, "TProfileControllerRemote");
	}

}
