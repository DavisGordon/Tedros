/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.parceiro.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.IEmpresaParceiraController;
import org.somos.parceiro.model.EmpresaParceira;
import org.somos.server.parceiro.service.EmpresaParceiraService;

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
@Stateless(name="IEmpresaParceiraController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EmpresaParceiraController extends TSecureEjbController<EmpresaParceira> implements IEmpresaParceiraController, ITSecurity {
	
	@EJB
	private EmpresaParceiraService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<EmpresaParceira> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
