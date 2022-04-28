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

import org.somos.ejb.controller.IAjudaCampanhaController;
import org.somos.model.AjudaCampanha;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;
import org.somos.server.campanha.service.AjudaCampanhaService;

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
@Stateless(name="IAjudaCampanhaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AjudaCampanhaController extends TSecureEjbController<AjudaCampanha> implements IAjudaCampanhaController, ITSecurity {
	
	@EJB
	private AjudaCampanhaService serv;
	
	@EJB
	private ITSecurityController securityController;

	@Override
	public ITEjbService<AjudaCampanha> getService() {
		return serv;
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	public TResult<List<AjudaCampanha>> recuperar(TAccessToken token, Campanha c, FormaAjuda fa, String p, Integer diasAtras){
		try {
			List<AjudaCampanha> lst = serv.recuperar(c, fa, p, diasAtras);
			return new TResult<>(EnumResult.SUCESS, lst);
		} catch (Exception e) {
			e.printStackTrace();
			return super.processException(token, null, e);
		}
	}
}
