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
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ISiteTermoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteTermoController extends TEjbController<SiteTermo> implements ISiteTermoController {
	
	@EJB
	private TStatelessService<SiteTermo> serv;
	
	@Override
	public ITEjbService<SiteTermo> getService() {
		return serv;
	}

	@Override
	public TResult<SiteTermo> save(SiteTermo e) {
		
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
				return processException(e, e1);
			}
		}
		
		return super.save(e);
	}
}
