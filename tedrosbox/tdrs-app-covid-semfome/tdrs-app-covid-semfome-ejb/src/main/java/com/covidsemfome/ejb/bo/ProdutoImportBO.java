
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.model.Produto;
import com.covidsemfome.model.ProdutoImport;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;

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
