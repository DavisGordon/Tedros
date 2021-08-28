package com.tedros.core.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.core.ejb.eao.TUserEao;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.bo.TGenericBO;

@RequestScoped
public class TUserBO extends TGenericBO<TUser> {
	
	@Inject
	private TUserEao eao;
	
	@Override
	public TUserEao getEao() {
		return eao;
	}

	public TUser getUserByLoginPassword(String login, String password) {
		return eao.getUserByLoginPassword(login, password);
	}
	
	public void saveActiveProfile(TProfile profile, Long userId) throws Exception{
		eao.saveActiveProfile(profile, userId);
	}
	

}
