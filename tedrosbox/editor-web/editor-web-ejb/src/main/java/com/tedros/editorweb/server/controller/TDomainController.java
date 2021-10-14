/**
 * 
 */
package com.tedros.editorweb.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.editorweb.model.Domain;
import com.tedros.editorweb.model.Page;
import com.tedros.editorweb.server.service.TEntityService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ITDomainController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TDomainController extends TSecureEjbController<Domain> 
implements ITDomainController, ITSecurity{

	@EJB
	private TEntityService<Domain> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	protected ITEjbService<Domain> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return this.securityController;
	}
	
	@Override
	public TResult<Domain> save(TAccessToken token, Domain e) {
		if(e.getPages()!=null)
			for(Page s : e.getPages())
				if(s.getDomain()==null)
					s.setDomain(e);
		return super.save(token, e);
	}


}
