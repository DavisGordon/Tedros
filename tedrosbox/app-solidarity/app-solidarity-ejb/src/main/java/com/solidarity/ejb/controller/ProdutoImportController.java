/**
 * 
 */
package com.solidarity.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.solidarity.ejb.controller.IProdutoImportController;
import com.solidarity.ejb.service.ProdutoImportService;
import com.solidarity.model.Produto;
import com.tedros.ejb.base.controller.TEjbImportController;
import com.tedros.ejb.base.service.ITEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IProdutoImportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoImportController extends TEjbImportController<Produto> implements IProdutoImportController {

	@EJB
	private ProdutoImportService serv;
	
	@Override
	public ITEjbImportService<Produto> getService() {
		return serv;
	}

	

}
