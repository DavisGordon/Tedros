/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.solidarity.ejb.controller.ISaidaController;
import com.solidarity.ejb.service.SaidaService;
import com.solidarity.model.Saida;
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
@Stateless(name="ISaidaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SaidaController extends TEjbController<Saida> implements ISaidaController {
	
	@EJB
	private SaidaService serv;
	
	@Override
	public ITEjbService<Saida> getService() {
		return serv;
	}
	
	@Override
	public TResult<Saida> save(Saida saida) {
		
		try{
			Saida saidaOld = null;
			if(!saida.isNew()) 
				saidaOld = serv.findById(saida);
					
			Saida res = serv.save(saida, saidaOld);
			
			return new TResult<>(EnumResult.SUCESS, res);
		}catch(Exception e) {
			return processException(saida, e);
		}
	}
	
	

}
