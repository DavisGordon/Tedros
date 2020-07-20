/**
 * 
 */
package com.covidsemfome.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.UserBO;
import com.covidsemfome.ejb.exception.UserNotFoundException;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IAutUserService")
public class AutUserService extends TEjbService<User>  {

	@Inject
	private UserBO bo;
	
	
	@Override
	public ITGenericBO<User> getBussinesObject() {
		return bo;
	}
	
	public Boolean validar(String key){
		return bo.validar(key);
	}
	
	public User login(String loginName, String pass ) throws Exception {
		return bo.login(loginName, pass);
	}

	
	public User recuperar(String key) {
		return bo.recuperar(key);
	}

	public void logout(Pessoa pessoa) {
		bo.logout(pessoa);
	}

}



