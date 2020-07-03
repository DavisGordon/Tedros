/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.TipoAjudaBO;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ITipoAjudaService")
public class TipoAjudaService extends TEjbService<TipoAjuda> implements ITipoAjudaService {
	
	@Inject
	private TipoAjudaBO bo;
	
	@Override
	public TipoAjudaBO getBussinesObject() {
		return bo;
	}
	
	public TResult<List<TipoAjuda>> listar(String tipo){
		try{
			List<TipoAjuda> lst = bo.listar(tipo);
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

}
