package com.tedros.core.ejb.bo;

import javax.persistence.EntityManager;

import com.tedros.core.ejb.eao.TUserEao;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.bo.TGenericBO;

public final class TUserBO extends TGenericBO<TUser> {
	
	private TUserEao eao = new TUserEao();
	
	@Override
	public TUserEao getEao() {
		return eao;
	}

	public TUser getUserByLoginPassword(EntityManager em, String login, String password) {
		return eao.getUserByLoginPassword(em, login, password);
	}
	
	public void saveActiveProfile(EntityManager em, TProfile profile, Long userId) throws Exception{
		eao.saveActiveProfile(em, profile, userId);
	}

}
