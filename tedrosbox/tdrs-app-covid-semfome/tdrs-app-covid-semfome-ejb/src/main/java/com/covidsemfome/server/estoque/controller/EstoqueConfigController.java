/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.IEstoqueConfigController;
import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.EstoqueConfig;
import com.covidsemfome.server.estoque.service.EstoqueConfigService;
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
@Stateless(name="IEstoqueConfigController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueConfigController extends TSecureEjbController<EstoqueConfig> implements IEstoqueConfigController, ITSecurity {
	
	@EJB
	private EstoqueConfigService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<EstoqueConfig> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	public TResult<List<EstoqueConfig>> pesquisar(TAccessToken token, Cozinha cozinha){
		try {
			List<EstoqueConfig> l = serv.pesquisar(cozinha);
			return new TResult<>(EnumResult.SUCESS, l);
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}

}
