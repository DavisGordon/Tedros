package org.tedros.core.ejb.service;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.cdi.bo.TUserBO;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@Local
@Stateless(name="TUserService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TUserService extends TEjbService<TUser> {

	@Inject
	private TUserBO bo;
	
	@EJB
	private TSecurityService serv;
	
	@Override
	public ITGenericBO<TUser> getBussinesObject() {
		return bo;
	}

	
	public TUser login(String login, String password) {
		TUser user = bo.getUserByLoginPassword(login, password);
		if(user!=null)
			serv.addUser(user);
		return user;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public TUser saveActiveProfile(TProfile profile, Long userId) throws Exception {
		TUser user = bo.saveActiveProfile(profile, userId);
		if(user!=null)
			serv.addUser(user);
		return user;
	}

	
}
