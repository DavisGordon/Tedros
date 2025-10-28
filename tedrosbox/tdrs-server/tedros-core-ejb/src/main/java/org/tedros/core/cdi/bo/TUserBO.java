package org.tedros.core.cdi.bo;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import org.tedros.core.cdi.eao.TUserEao;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.cdi.bo.TGenericBO;

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
	
	public TUser saveActiveProfile(TProfile profile, Long userId) throws Exception{
		TUser user = new TUser();
		user.setId(userId);
		user = findById(user);
		user.setActiveProfile(profile);
		save(user);
		return user;
	}
	

}
