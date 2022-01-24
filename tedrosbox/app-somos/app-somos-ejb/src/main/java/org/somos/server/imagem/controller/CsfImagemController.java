/**
 * 
 */
package org.somos.server.imagem.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.ICsfImagemController;
import org.somos.model.CsfImagem;
import org.somos.server.base.service.TStatelessService;

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
@Stateless(name="ICsfImagemController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CsfImagemController extends TSecureEjbController<CsfImagem> implements ICsfImagemController, ITSecurity{

	@EJB
	private TStatelessService<CsfImagem> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<CsfImagem> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	

}
