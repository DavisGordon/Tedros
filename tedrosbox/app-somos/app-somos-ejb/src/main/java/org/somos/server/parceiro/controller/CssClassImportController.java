/**
 * 
 */
package org.somos.server.parceiro.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.somos.ejb.controller.ICssClassImportController;
import org.somos.parceiro.model.CssClass;
import org.somos.server.parceiro.service.CssClassImportService;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TEjbImportController;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ICssClassImportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CssClassImportController extends TEjbImportController<CssClass> implements ICssClassImportController {

	@EJB
	private CssClassImportService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbImportService<CssClass> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

	

}
