/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.solidarity.ejb.controller.ISiteAboutController;
import com.solidarity.ejb.service.TStatelessService;
import com.solidarity.model.SiteAbout;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ISiteAboutController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteAboutController extends TEjbController<SiteAbout> implements ISiteAboutController {
	
	@EJB
	private TStatelessService<SiteAbout> serv;
	
	@Override
	public ITEjbService<SiteAbout> getService() {
		return serv;
	}

	@Override
	public TResult<SiteAbout> save(SiteAbout e) {
		
		if(e.getStatus().equals("ATIVADO")) {
			SiteAbout ex = new SiteAbout();
			ex.setStatus("ATIVADO");
			try {
				List<SiteAbout> l = serv.findAll(ex);
				if(l!=null)
					for(SiteAbout i : l){
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
