/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.TStatelessService;
import com.covidsemfome.model.SiteTermo;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="ISiteTermoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteTermoController extends TSecureEjbController<SiteTermo> implements ISiteTermoController, ITSecurity {
	
	@EJB
	private TStatelessService<SiteTermo> serv;

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SiteTermo> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}


	@Override
	public TResult<SiteTermo> save(TAccessToken token, SiteTermo e) {
		
		if(e.getStatus().equals("ATIVADO")) {
			SiteTermo ex = new SiteTermo();
			ex.setStatus("ATIVADO");
			try {
				List<SiteTermo> l = serv.findAll(ex);
				if(l!=null)
					for(SiteTermo i : l){
						if(!e.isNew() && i.getId().equals(e.getId())) 
							continue;
						i.setStatus("DESATIVADO");
						serv.save(i);
					}
			} catch (Exception e1) {
				return processException(token, e, e1);
			}
		}
		
		return super.save(token, e);
	}
}
