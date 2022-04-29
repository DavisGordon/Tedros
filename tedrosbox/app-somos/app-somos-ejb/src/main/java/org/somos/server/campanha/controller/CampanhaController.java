/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.campanha.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.ICampanhaController;
import org.somos.model.Campanha;
import org.somos.server.campanha.service.CampanhaService;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@TRemoteSecurity
@Stateless(name="ICampanhaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CampanhaController extends TSecureEjbController<Campanha> implements ICampanhaController, ITSecurity {
	
	@EJB
	private CampanhaService serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public CampanhaService getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	

	public TResult<List<Campanha>> listarValidos(TAccessToken token){
		try {
			return new TResult<>(EnumResult.SUCESS, serv.listarValidos());
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}

	
}
