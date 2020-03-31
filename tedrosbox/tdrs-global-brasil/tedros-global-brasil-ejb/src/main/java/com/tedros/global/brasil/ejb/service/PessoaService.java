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

import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;
import com.tedros.global.brasil.ejb.bo.PessoaBO;
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
	
	private PessoaBO bo = new PessoaBO();
	
	@Override
	public PessoaBO getBussinesObject() {
		return bo;
	}
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		try{
			List<Pessoa> list = getBussinesObject().pesquisar(getEntityManager(), nome, dataNascimento, tipo, tipoDocumento, numero);
			return new TResult<List<Pessoa>>(EnumResult.SUCESS, list);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<Pessoa>>(EnumResult.ERROR, e.getMessage());
		}
		
	}

	
}
