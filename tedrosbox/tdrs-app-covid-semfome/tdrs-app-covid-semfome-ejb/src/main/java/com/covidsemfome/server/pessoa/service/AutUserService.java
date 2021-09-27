/**
 * 
 */
package com.covidsemfome.server.pessoa.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.covidsemfome.server.pessoa.bo.UserBO;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IAutUserService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
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
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public User login(String loginName, String pass ) throws Exception {
		return bo.login(loginName, pass);
	}

	
	public User recuperar(String key) {
		return bo.recuperar(key);
	}

	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void logout(Pessoa pessoa) {
		bo.logout(pessoa);
	}

}



