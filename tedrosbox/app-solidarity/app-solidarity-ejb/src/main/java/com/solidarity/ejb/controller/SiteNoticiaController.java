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

import com.solidarity.ejb.controller.ISiteNoticiaController;
import com.solidarity.ejb.service.TStatelessService;
import com.solidarity.model.SiteNoticia;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ISiteNoticiaController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteNoticiaController extends TEjbController<SiteNoticia> implements ISiteNoticiaController {
	
	@EJB
	private TStatelessService<SiteNoticia> serv;
	
	@Override
	public ITEjbService<SiteNoticia> getService() {
		return serv;
	}

}
