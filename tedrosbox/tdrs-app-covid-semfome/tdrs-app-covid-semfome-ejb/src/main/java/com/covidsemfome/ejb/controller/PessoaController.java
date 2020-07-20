/**
 * 
 */
package com.covidsemfome.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import com.covidsemfome.ejb.exception.EmailBusinessException;
import com.covidsemfome.ejb.producer.Item;
import com.covidsemfome.ejb.service.PessoaService;
import com.covidsemfome.ejb.service.exception.PessoaContatoExistException;
import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.util.TEncriptUtil;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IPessoaController")
public class PessoaController extends TEjbController<Pessoa> implements IPessoaController {

	@EJB
	private PessoaService serv;
	
	@Inject
	@Named("errorMsg")
	private Item<String> errorMsg;
	
	@Override
	public PessoaService getService() {
		return serv;
	}
	
	@Override
	public TResult<Boolean> defpass(String key, String pass) {
		try{
			
			Pessoa p = new Pessoa();
			p.setNewPassKey(key);
			
			p = serv.find(p);
			if(p==null)
				return new TResult<>(EnumResult.WARNING, "A chave informada não confere!");
			
			
			p.setPassword(TEncriptUtil.encript(pass));
			p.setNewPassKey(null);
			
			p = serv.save(p);
			
			return new TResult<>(EnumResult.SUCESS, true);
			
		}catch(NoResultException e){
			return new TResult<>(EnumResult.WARNING, "A chave informada não confere!");
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<Boolean> validateNewPassKey(String key) {
		try{
			
			Pessoa p = new Pessoa();
			p.setNewPassKey(key);
			
			p = serv.find(p);
			if(p!=null)
				return new TResult<>(EnumResult.SUCESS, true);
			else
				return new TResult<>(EnumResult.WARNING, "A chave informada não confere!");
			
		}catch(NoResultException e){
			return new TResult<>(EnumResult.WARNING, "A chave informada não confere!");
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<Boolean> newPass(String email) {
		
		try{
			
			Pessoa p = new Pessoa();
			p.setLoginName(email);
			
			p = serv.find(p);
			if(p==null)
				return new TResult<>(EnumResult.WARNING, "O email informado não confere!");
			
			String key = serv.newPass(p);
			serv.enviarEmailNewPass(p, key);
			
			return new TResult<>(EnumResult.SUCESS, true);
			
		}catch(TSentEmailException e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, errorMsg.getValue());
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		} 
	}
	
	@Override
	public TResult<Pessoa> remove(Pessoa entidade) {
		
		try{
			if(serv.isVoluntario(entidade)){
				Pessoa e = serv.desativar(entidade);
				return new TResult<Pessoa>(EnumResult.WARNING, true, "O registro foi desativado por ser voluntário "
						+ "em campanhas e sua exclusão resultara em perda de informações.", e);
			}else{
				serv.remove(entidade);
				return new TResult<Pessoa>(EnumResult.SUCESS);
			}

		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Pessoa>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public TResult recuperar(String loginName, String password){
		try{
			Pessoa pess = getService().recuperar(loginName, password);
			return new TResult<Pessoa>(EnumResult.SUCESS, pess);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Pessoa>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		try{
			List<Pessoa> list = getService().pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
			return new TResult<List<Pessoa>>(EnumResult.SUCESS, list);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<Pessoa>>(EnumResult.ERROR, e.getMessage());
		}
		
	}
	
	public TResult<Pessoa> saveFromSite(Pessoa entidade) {
		try{
			
			if(serv.isLoginEmUso(entidade)){
				return new TResult<>(EnumResult.WARNING, "Este email já se encontra em uso mas se esqueceu a senha "
						+ "clique em 'Renovar senha' na pagina de login do painel.");
			}
			
			entidade = serv.save(entidade);
			serv.enviarEmailCadastrado(entidade);
			
			return new TResult<>(EnumResult.SUCESS, entidade);
			
		}catch(PessoaContatoExistException e){
			return new TResult<Pessoa>(EnumResult.WARNING);
		} catch (TSentEmailException | EmailBusinessException  e) {
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, errorMsg.getValue());
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, errorMsg.getValue());
		}
	}

	

	

}
