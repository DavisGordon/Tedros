package com.tedros.core.ejb.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TUserBO;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;


@Stateless(name="TUserService")
public class TUserServiceImpl extends TEjbService<TUser>	implements	TUserService {

	@Inject
	private TUserBO bo;
	
	@Override
	public ITGenericBO<TUser> getBussinesObject() {
		return bo;
	}

	@Override
	public TResult<TUser> login(String login, String password) {
		try{
			TUser entity = bo.getUserByLoginPassword(login, password);
			return new TResult<TUser>(EnumResult.SUCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(EnumResult.ERROR, e.getMessage());
		}
	}
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public TResult<TUser> saveActiveProfile(TProfile profile, Long userId) {
		try{
			bo.saveActiveProfile(profile, userId);
			return new TResult<TUser>(EnumResult.SUCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TUser>(EnumResult.ERROR, e.getMessage());
		}
	}

	
}
