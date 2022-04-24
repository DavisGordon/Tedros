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
import org.somos.model.Pessoa;
import org.somos.server.base.service.TStatelessService;
import org.somos.server.campanha.service.AssociadoService;

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
 **/
@TRemoteSecurity
@Stateless(name="IAssociadoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AssociadoController extends TSecureEjbController<Associado> implements IAssociadoController, ITSecurity {
	
	@EJB
	private AssociadoService serv;
	
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

	@Override
	public TResult<Associado> recuperar(TAccessToken token, Pessoa p) {
		try {
			Associado e = serv.recuperar(p);
			return new TResult<>(EnumResult.SUCESS, e);
		}catch(Exception e) {
			return super.processException(token, null, e);
		}
	}

	
}
