/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.IEntradaController;
import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Entrada;
import com.covidsemfome.server.estoque.service.EntradaService;
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
@Stateless(name="IEntradaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntradaController extends TSecureEjbController<Entrada> implements IEntradaController, ITSecurity {
	
	@EJB
	private EntradaService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Entrada> getService() {
		return serv;
	}
	
	public TResult<List<Entrada>> pesquisar(TAccessToken token, Cozinha coz, Date dataInicio, Date dataFim, String tipo, String orderby, String ordertype, Long... idsl){
		try{
			List<Entrada> lst = serv.pesquisar(idsl!=null ? Arrays.asList(idsl) : null, coz, dataInicio, dataFim, tipo, orderby, ordertype);
			return new TResult<>(EnumResult.SUCESS, lst);
		}catch(Exception e) {
			return processException(token, null, e);
		}
	}
	
	@Override
	public TResult<Entrada> save(TAccessToken token, Entrada entrada) {
		
		try{
			Entrada entradaOld = null;
			if(!entrada.isNew()) 
				entradaOld = serv.findById(entrada);
					
			Entrada res = serv.save(entrada, entradaOld);
			
			return new TResult<>(EnumResult.SUCESS, res);
		}catch(Exception e) {
			return processException(token, entrada, e);
		}
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}
