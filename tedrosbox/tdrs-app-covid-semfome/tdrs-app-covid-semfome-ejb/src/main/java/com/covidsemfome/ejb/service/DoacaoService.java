/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.DoadorBO;
import com.covidsemfome.model.Doador;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@Stateless(name="TDoadorService")
public class DoadorService extends TEjbService<Doador> implements IDoadorService {
	
	@Inject
	private DoadorBO bo;
	
	@Override
	public DoadorBO getBussinesObject() {
		return bo;
	}
	
	@SuppressWarnings("rawtypes")
	public TResult pesquisar(String nome, Date dataNascimento){
		try{
			List<Doador> list = getBussinesObject().pesquisar(nome, dataNascimento);
			return new TResult<List<Doador>>(EnumResult.SUCESS, list);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<Doador>>(EnumResult.ERROR, e.getMessage());
		}
		
	}

	
}
