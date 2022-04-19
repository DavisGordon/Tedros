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

import org.somos.ejb.controller.IAssociadoController;
import org.somos.model.Associado;
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
@Stateless(name="IAssociadoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AssociadoController extends TSecureEjbController<Associado> implements IAssociadoController, ITSecurity {
	
	@EJB
	private TStatelessService<Associado> serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<Associado> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	
}
