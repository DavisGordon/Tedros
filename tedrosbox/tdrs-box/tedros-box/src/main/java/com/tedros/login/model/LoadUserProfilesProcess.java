/**
 * 
 */
package com.tedros.login.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadUserProfilesProcess extends TOptionsProcess {

	/**
	 * @param entityType
	 * @param serviceJndiName
	 * @param remoteMode
	 * @throws TProcessException
	 */
	public LoadUserProfilesProcess(Class<? extends ITEntity> entityType, String serviceJndiName, boolean remoteMode)
			throws TProcessException {
		super(entityType, serviceJndiName, remoteMode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param entityType
	 * @throws TProcessException
	 */
	public LoadUserProfilesProcess(Class<? extends ITEntity> entityType) throws TProcessException {
		super(entityType);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.process.TOptionsProcess#doFilter(java.util.Map)
	 */
	@Override
	public List<TResult<Object>> doFilter(Map<String, Object> objects) {
		TUser u = TedrosContext.getLoggedUser();
		List<TResult<Object>> l = new ArrayList<>();
		for(TProfile p : u.getProfiles())
		 l.add(new TResult<Object>(EnumResult.SUCESS, p));
		return l;
	}

}
