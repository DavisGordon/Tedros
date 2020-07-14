/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.global.brasil.ejb.bo.PessoaBO;
import com.tedros.global.brasil.model.Contato;
import com.tedros.global.brasil.model.Pessoa;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Singleton
@Stateless(name = "TPessoaService")
public class PessoaService extends TEjbService<Pessoa> implements IPessoaService {
	
	@Inject
	private PessoaBO bo;
	
	@Override
	public PessoaBO getBussinesObject() {
		return bo;
	}
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		try{
			List<Pessoa> list = getBussinesObject().pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
			return new TResult<List<Pessoa>>(EnumResult.SUCESS, list);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<Pessoa>>(EnumResult.ERROR, e.getMessage());
		}
		
	}

	public TResult<Pessoa> saveFromSite(Pessoa entidade) {
		
		
		String email = null;
		String tel = null;
		if(entidade.getContatos()!=null){
			for(Contato c : entidade.getContatos()){
				if(c.getTipo()!=null && c.getTipo().equals("1"))
					email = c.getDescricao();
				if(c.getTipo()!=null && c.getTipo().equals("2"))
					tel = c.getDescricao();
			}
		}
		
		if(bo.isPessoaContatoExiste(entidade.getNome(), email, tel)){
			return new TResult<Pessoa>(EnumResult.WARNING);
		}
		
		return null;//super.save(entidade);
	}
	
}
