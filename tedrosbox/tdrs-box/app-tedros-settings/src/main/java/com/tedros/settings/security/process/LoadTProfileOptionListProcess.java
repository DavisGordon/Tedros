/**
 * 
 */
package com.tedros.settings.security.process;

import java.util.List;
import java.util.Map;

import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadTProfileOptionListProcess extends TOptionsProcess {

	public LoadTProfileOptionListProcess() throws TProcessException {
		super(TProfile.class, "TProfileServiceRemote", true);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.process.TOptionsProcess#doFilter(java.util.Map)
	 */
	@Override
	public List<TResult<Object>> doFilter(Map<String, Object> objects) {
		// TODO Auto-generated method stub
		return null;
	}

}
