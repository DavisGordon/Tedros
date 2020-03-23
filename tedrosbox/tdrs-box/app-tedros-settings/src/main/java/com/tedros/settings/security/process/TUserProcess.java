/**
 * 
 */
package com.tedros.settings.security.process;

import java.util.List;

import com.tedros.core.ejb.service.TUserService;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TUserProcess extends TEntityProcess<TUser> {
	
	private String login;
	private String password;
	
	private TProfile profile;
	private Long userId;
	
	public TUserProcess() throws TProcessException {
		super(TUser.class, "TUserServiceRemote", true);
	} 

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.process.TEntityProcess#execute(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute(List<TResult<TUser>> resultList) {
		
		if(login!=null && password!=null){
			TUserService service = (TUserService) getService();
			resultList.add(service.login(login, password));
			login = null;
			password = null;
		}
		
		if(profile!=null && userId!=null){
			TUserService service = (TUserService) getService();
			resultList.add(service.saveActiveProfile(profile, userId));
			profile = null;
			userId = null;
		}
	}

	public void setProfile(TProfile profile) {
		this.profile = profile;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
