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
import com.covidsemfome.model.SiteDoacao;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ISiteDoacaoController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteDoacaoController extends TEjbController<SiteDoacao> implements ISiteDoacaoController {
	
	@EJB
	private TStatelessService<SiteDoacao> serv;
	
	@Override
	public ITEjbService<SiteDoacao> getService() {
		return serv;
	}

}
