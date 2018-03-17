/**
 * 
 */
package com.tedros.settings.security.process;

import java.util.List;

import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TProfileProcess extends TEntityProcess<TProfile> {

	public TProfileProcess() throws TProcessException {
		super(TProfile.class, "TProfileServiceRemote", true);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.process.TEntityProcess#execute(java.util.List)
	 */
	@Override
	public void execute(List<TResult<TProfile>> resultList) {

	}

}
