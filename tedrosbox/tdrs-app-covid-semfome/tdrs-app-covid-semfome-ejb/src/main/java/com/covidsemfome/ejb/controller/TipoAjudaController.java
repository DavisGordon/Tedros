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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.TipoAjudaService;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ITipoAjudaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TipoAjudaController extends TSecureEjbController<TipoAjuda> implements ITipoAjudaController, ITSecurity {
	
	@EJB
	private TipoAjudaService serv;

	@EJB
	private ITSecurityController securityController;
	
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

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
