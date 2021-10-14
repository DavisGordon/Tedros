/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.editorweb.server.service.TEntityService;
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
@Stateless(name="ITComponentTemplateController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TComponentTemplateController extends TSecureEjbController<ComponentTemplate> 
implements ITComponentTemplateController, ITSecurity{

	@EJB
	private TEntityService<ComponentTemplate> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<ComponentTemplate> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	


}
