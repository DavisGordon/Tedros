/**
 * 
 */
package com.covidsemfome.server.parceiro.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.controller.IComponentTemplateController;
import com.covidsemfome.parceiro.model.ComponentTemplate;
import com.covidsemfome.server.base.service.TStatelessService;
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
@Stateless(name="IComponentTemplateController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ComponentTemplateController extends TSecureEjbController<ComponentTemplate> 
implements IComponentTemplateController, ITSecurity{

	@EJB
	private TStatelessService<ComponentTemplate> serv;
	
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
