/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.solidarity.ejb.controller.ISiteVideoController;
import com.solidarity.ejb.service.TStatelessService;
import com.solidarity.model.SiteVideo;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ISiteVideoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteVideoController extends TEjbController<SiteVideo> implements ISiteVideoController {
	
	@EJB
	private TStatelessService<SiteVideo> serv;
	
	@Override
	public ITEjbService<SiteVideo> getService() {
		return serv;
	}

}
