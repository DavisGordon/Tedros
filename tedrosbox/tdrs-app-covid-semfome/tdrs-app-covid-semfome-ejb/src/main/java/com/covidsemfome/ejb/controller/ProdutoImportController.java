/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.service.ProdutoImportService;
import com.covidsemfome.model.Produto;
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
