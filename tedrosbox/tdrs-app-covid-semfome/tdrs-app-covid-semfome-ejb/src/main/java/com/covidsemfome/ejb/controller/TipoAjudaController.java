/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.covidsemfome.ejb.service.TipoAjudaService;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ITipoAjudaController")
public class TipoAjudaController extends TEjbController<TipoAjuda> implements ITipoAjudaController {
	
	@EJB
	private TipoAjudaService serv;
	
	
	
	public TResult<List<TipoAjuda>> listar(String tipo){
		try{
			List<TipoAjuda> lst = serv.listar(tipo);
			return new TResult<>(EnumResult.SUCESS, "", lst);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}



	@Override
	public ITEjbService<TipoAjuda> getService() {
		return serv;
	}

}
