/**
 * 
 */
package org.somos.server.produto.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.Produto;
import org.somos.server.produto.bo.ProdutoImportBO;

import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.ejb.base.service.TEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="ProdutoImportService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProdutoImportService extends TEjbImportService<Produto> {

	@Inject
	private ProdutoImportBO bo;
	
	@Override
	public TImportFileEntityBO<Produto> getBusinessObject() {
		return bo;
	}

}
