/**
 * 
 */
package com.covidsemfome.server.pessoa.bo;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.User;
import com.covidsemfome.server.exception.UserNotFoundException;
import com.covidsemfome.server.pessoa.eao.UserEAO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.util.TEncriptUtil;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class UserBO extends TGenericBO<User> {

	@Inject
	private UserEAO eao;
	
	@Inject
	private PessoaBO bo;
	
	
	@Override
	public ITGenericEAO<User> getEao() {
		return eao;
	}
	
	public User recuperar(String key){
		return eao.recuperar(key);
	}
	
	public boolean validar(String key){
		try{
			User u = eao.recuperar(key);
			return (u!=null);
		}catch(NoResultException e){
			return false;
		}
	}
	
	public void logout(Pessoa p){
		eao.remover(p);
	}
	
	public User login(String loginName, String pass ) throws Exception{
		
		try{
			Pessoa pess = bo.recuperar(loginName, TEncriptUtil.encript(pass));
			
			try{
				eao.remover(pess);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String key = UUID.randomUUID().toString(); 
			User user = new User();
			user.setKey(key);
			user.setPessoa(pess);
			
			return this.save(user);
		}catch(NoResultException e){
			throw new UserNotFoundException();
		}
	}
	
	public List<User> recuperar(Pessoa pessoa){
		return eao.recuperar(pessoa);
	}

}
