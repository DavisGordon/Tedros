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

import org.somos.ejb.controller.ICampanhaMailConfigController;
import org.somos.model.CampanhaMailConfig;
import org.somos.server.campanha.service.CampanhaMailConfigService;

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
@Stateless(name="ICampanhaMailConfigController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CampanhaMailConfigController extends TSecureEjbController<CampanhaMailConfig> implements ICampanhaMailConfigController, ITSecurity {
	
	@EJB
	private CampanhaMailConfigService serv;
	
	@EJB
	private ITSecurityController securityController;
	

	@Override
	public ITEjbService<CampanhaMailConfig> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<Boolean> processarMailing(TAccessToken token) {
		try{
			serv.processarMailing();
			return new TResult<>(EnumResult.SUCESS, true);
		}catch(Exception e) {
			return super.processException(token, null, e);
		}
	}


	
}
