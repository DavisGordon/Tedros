
package com.template.server.produto.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.template.model.Produto;
import com.template.model.ProdutoImport;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ProdutoImportBO extends TImportFileEntityBO<Produto> {

	@Inject
	private ProdutoBO bo;
	
	@Override
	protected Class<Produto> getEntityClass() {
		return Produto.class;
	}

	@Override
	protected ITGenericBO<Produto> getBusinessObject() {
		return bo;
	}

	
	@Override
	public Class<ProdutoImport> getImportModelClass() {
		return ProdutoImport.class;
	}

}
