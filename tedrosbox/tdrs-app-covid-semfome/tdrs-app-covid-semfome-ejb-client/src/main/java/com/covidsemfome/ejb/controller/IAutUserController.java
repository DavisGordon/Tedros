/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.result.TResult;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAutUserController extends ITEjbController<User> {
	
	public TResult<Boolean> logout(Pessoa pessoa);
	
	public TResult<Boolean> validar(String key);
	
	public TResult<User> login(String loginName, String pass );
	
	public TResult<User> recuperar(String key);
	
}
