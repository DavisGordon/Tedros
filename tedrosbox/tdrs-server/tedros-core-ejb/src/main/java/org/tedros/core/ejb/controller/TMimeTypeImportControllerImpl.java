/**
 * 
 */
package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.common.model.TMimeType;
import org.tedros.core.controller.TMimeTypeImportController;
import org.tedros.core.ejb.service.TMimeTypeImportService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TEjbImportController;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="TMimeTypeImportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TMimeTypeImportControllerImpl extends TEjbImportController<TMimeType> implements TMimeTypeImportController {

	@EJB
	private TMimeTypeImportService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbImportService<TMimeType> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

	

}
