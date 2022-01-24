/**
 * 
 */
package org.somos.server.parceiro.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.ICssClassController;
import org.somos.parceiro.model.CssClass;
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
@Stateless(name="ICssClassController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CssClassController extends TSecureEjbController<CssClass> implements ICssClassController, ITSecurity{

	@EJB
	private TStatelessService<CssClass> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<CssClass> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	

}
