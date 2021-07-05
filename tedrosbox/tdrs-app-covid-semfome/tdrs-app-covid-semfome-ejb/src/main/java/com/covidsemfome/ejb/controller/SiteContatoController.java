/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.TStatelessService;
import com.covidsemfome.model.SiteContato;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ISiteContatoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteContatoController extends TEjbController<SiteContato> implements ISiteContatoController {
	
	@EJB
	private TStatelessService<SiteContato> serv;
	
	@Override
	public ITEjbService<SiteContato> getService() {
		return serv;
	}

}
