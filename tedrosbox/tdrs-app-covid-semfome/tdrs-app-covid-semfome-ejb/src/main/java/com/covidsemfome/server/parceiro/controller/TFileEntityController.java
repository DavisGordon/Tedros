/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.parceiro.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.ITFileEntityController;
import com.covidsemfome.server.base.service.TStatelessService;
import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
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
@Stateless(name="ITFileEntityController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TFileEntityController extends TSecureEjbController<TFileEntity> implements ITFileEntityController, ITSecurity {
	
	@EJB
	private TStatelessService<TFileEntity> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<TFileEntity> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
