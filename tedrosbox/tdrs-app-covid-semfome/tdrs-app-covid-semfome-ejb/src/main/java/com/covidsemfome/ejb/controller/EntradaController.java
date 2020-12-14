/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.EntradaService;
import com.covidsemfome.model.Entrada;
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
@Stateless(name="IEntradaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntradaController extends TEjbController<Entrada> implements IEntradaController {
	
	@EJB
	private EntradaService serv;
	
	@Override
	public ITEjbService<Entrada> getService() {
		return serv;
	}
	
	@Override
	public TResult<Entrada> save(Entrada entrada) {
		
		try{
			Entrada entSaved = null;
			if(!entrada.isNew()) 
				entSaved = serv.findById(entrada);
					
			Entrada res = serv.save(entrada, entSaved);
			
			return new TResult<>(EnumResult.SUCESS, res);
		}catch(Exception e) {
			return processException(entrada, e);
		}
	}
}
