/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.campanha.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IValorAjudaController;
import org.somos.model.ValorAjuda;
import org.somos.server.base.service.TStatelessService;

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
 **/
@TRemoteSecurity
@Stateless(name="IValorAjudaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ValorAjudaController extends TSecureEjbController<ValorAjuda> implements IValorAjudaController, ITSecurity {
	
	@EJB
	private TStatelessService<ValorAjuda> serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<ValorAjuda> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	
}
