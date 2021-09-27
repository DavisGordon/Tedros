/**
 * 
 */
package com.covidsemfome.server.pessoa.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import com.covidsemfome.ejb.controller.IPessoaController;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.server.exception.EmailBusinessException;
import com.covidsemfome.server.exception.PessoaContatoExistException;
import com.covidsemfome.server.pessoa.service.PessoaService;
import com.covidsemfome.server.producer.Item;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.util.TEncriptUtil;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="IPessoaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PessoaController extends TSecureEjbController<Pessoa> implements IPessoaController, ITSecurity {

	@EJB
	private PessoaService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Inject
	@Named("errorMsg")
	private Item<String> errorMsg;
	
	@Override
	public PessoaService getService() {
		return serv;
	}
	
	@Override
	public TResult<Boolean> defpass(TAccessToken token, String key, String pass) {
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
	public TResult<Boolean> validateNewPassKey(TAccessToken token, String key) {
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
	public TResult<Boolean> newPass(TAccessToken token, String email) {
		
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
	public TResult<Pessoa> remove(TAccessToken token, Pessoa entidade) {
		
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
	public TResult recuperar(TAccessToken token, String loginName, String password){
		try{
			Pessoa pess = getService().recuperar(loginName, password);
			return new TResult<Pessoa>(EnumResult.SUCESS, pess);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Pessoa>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(TAccessToken token, String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		try{
			List<Pessoa> list = getService().pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
			return new TResult<List<Pessoa>>(EnumResult.SUCESS, list);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<Pessoa>>(EnumResult.ERROR, e.getMessage());
		}
		
	}
	
	public TResult<Pessoa> saveFromSite(TAccessToken token, Pessoa entidade) {
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

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<Boolean> isLoginInUse(TAccessToken token, String email) {
		Pessoa ex = new Pessoa();
		ex.setLoginName(email);
		try {
			Boolean f = serv.isLoginEmUso(ex);
			return new TResult<>(EnumResult.SUCESS, f);
		} catch (Exception e) {
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, errorMsg.getValue());
		}
	}

	

}
