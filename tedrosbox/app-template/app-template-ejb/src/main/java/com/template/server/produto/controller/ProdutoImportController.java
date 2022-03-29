/**
 * 
 */
package com.template.server.produto.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TEjbImportController;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbImportService;
import com.template.ejb.controller.IProdutoImportController;
import com.template.model.Produto;
import com.template.server.produto.service.ProdutoImportService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="IProdutoImportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoImportController extends TEjbImportController<Produto> implements IProdutoImportController {

	@EJB
	private ProdutoImportService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbImportService<Produto> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

	

}
