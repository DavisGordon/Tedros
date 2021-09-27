/**
 * 
 */
package com.covidsemfome.server.pessoa.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.IAutUserController;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.covidsemfome.server.exception.UserNotFoundException;
import com.covidsemfome.server.pessoa.service.AutUserService;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IAutUserController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AutUserController extends TEjbController<User> implements IAutUserController {

	@EJB
	private AutUserService serv;
	
	public TResult<Boolean> validar(String key){
		try{
			boolean v = serv.validar(key);
			return new TResult<Boolean>(EnumResult.SUCESS, "", v)  ;
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Boolean>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<User> login(String loginName, String pass ) {
		
		try {
			User u = serv.login(loginName, pass);
			return new TResult<User>(EnumResult.SUCESS, "Olá "+u.getPessoa().getNome(), u);
		}catch (Exception e) {
			if(e.getCause() instanceof UserNotFoundException)
				return new TResult<User>(EnumResult.WARNING, "Email ou senha não conferem!");
			
			e.printStackTrace();
			return new TResult<User>(EnumResult.ERROR, e.getMessage());
		}
		
	}

	@Override
	public TResult<User> recuperar(String key) {
		try{
			User v = serv.recuperar(key);
			return new TResult<>(EnumResult.SUCESS, "", v)  ;
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<Boolean> logout(Pessoa pessoa) {
		try{
			serv.logout(pessoa);
			return new TResult<Boolean>(EnumResult.SUCESS, "", true)  ;
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Boolean>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public ITEjbService<User> getService() {
		return serv;
	}

	

}



