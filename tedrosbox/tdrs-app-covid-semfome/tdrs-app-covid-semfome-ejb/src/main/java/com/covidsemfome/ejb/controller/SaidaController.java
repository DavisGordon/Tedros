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

import com.covidsemfome.ejb.service.SaidaService;
import com.covidsemfome.model.Saida;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ISaidaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SaidaController extends TSecureEjbController<Saida> implements ISaidaController, ITSecurity {
	
	@EJB
	private SaidaService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Saida> getService() {
		return serv;
	}
	
	@Override
	public TResult<Saida> save(TAccessToken token, Saida saida) {
		
		try{
			Saida saidaOld = null;
			if(!saida.isNew()) 
				saidaOld = serv.findById(saida);
					
			Saida res = serv.save(saida, saidaOld);
			
			return new TResult<>(EnumResult.SUCESS, res);
		}catch(Exception e) {
			return processException(token, saida, e);
		}
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
