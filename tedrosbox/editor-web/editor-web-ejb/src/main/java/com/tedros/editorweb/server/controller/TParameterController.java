/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.editorweb.model.Parameter;
import com.tedros.editorweb.server.service.TEntityService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ITParameterController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TParameterController extends TSecureEjbController<Parameter> implements ITParameterController, ITSecurity{

	@EJB
	private TEntityService<Parameter> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<Parameter> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	

}
