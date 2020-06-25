/**
 * 
 */
package com.covidsemfome.ejb.service;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.UserBO;
import com.covidsemfome.ejb.exception.UserNotFoundException;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@Singleton
@Stateless(name="IAutUserService")
public class AutUserService extends TEjbService<User> implements IAutUserService {

	@Inject
	private UserBO bo;
	
	@Override
	public ITGenericBO<User> getBussinesObject() {
		return bo;
	}
	
	public TResult<Boolean> validar(String key){
		try{
			boolean v = bo.validar(key);
			return new TResult<Boolean>(EnumResult.SUCESS, "", v)  ;
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Boolean>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<User> login(String loginName, String pass ) {
		
		try {
			User u = bo.login(loginName, pass);
			return new TResult<User>(EnumResult.SUCESS, "Olá "+u.getPessoa().getNome(), u);
		} catch(UserNotFoundException e){
			return new TResult<User>(EnumResult.WARNING, "Email ou senha não conferem!");
		}catch (Exception e) {
			e.printStackTrace();
			return new TResult<User>(EnumResult.ERROR, e.getMessage());
		}
		
	}

	@Override
	public TResult<User> recuperar(String key) {
		try{
			User v = bo.recuperar(key);
			return new TResult<>(EnumResult.SUCESS, "", v)  ;
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<Boolean> logout(Pessoa pessoa) {
		try{
			bo.logout(pessoa);
			return new TResult<Boolean>(EnumResult.SUCESS, "", true)  ;
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Boolean>(EnumResult.ERROR, e.getMessage());
		}
	}

}



