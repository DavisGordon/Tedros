/**
 * 
 */
package com.covidsemfome.ejb.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.covidsemfome.ejb.service.PessoaService;
import com.covidsemfome.ejb.service.exception.PessoaContatoExistException;
import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IPessoaController")
public class PessoaController extends TEjbController<Pessoa> implements IPessoaController {

	@EJB
	private PessoaService serv;
	
	@Override
	public PessoaService getService() {
		return serv;
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
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
