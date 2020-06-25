/**
 * 
 */
package com.covidsemfome.ejb.service;

import javax.ejb.Remote;

import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.base.service.TResult;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAutUserService extends ITEjbService<User> {
	
	public TResult<Boolean> logout(Pessoa pessoa);
	
	public TResult<Boolean> validar(String key);
	
	public TResult<User> login(String loginName, String pass );
	
	public TResult<User> recuperar(String key);
}
