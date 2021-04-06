package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.core.ejb.service.TUserService;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;


@Stateless(name="TUserController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TUserControllerImpl extends TEjbController<TUser>	implements	TUserController {

	@EJB
	private TUserService serv;
	
	@Override
	public TResult<TUser> login(String login, String password) {
		try{
			TUser entity = serv.login(login, password);
			return new TResult<TUser>(EnumResult.SUCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<TUser> saveActiveProfile(TProfile profile, Long userId) {
		try{
			serv.saveActiveProfile(profile, userId);
			return new TResult<TUser>(EnumResult.SUCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(EnumResult.ERROR, e.getMessage());
		}
	}
	@Override
	public ITEjbService<TUser> getService() {
		return serv;
	}

	
}
