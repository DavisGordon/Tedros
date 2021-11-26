
package com.covidsemfome.server.parceiro.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.parceiro.model.CssClass;
import com.covidsemfome.parceiro.model.CssClassImport;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CssClassImportBO extends TImportFileEntityBO<CssClass> {

	@Inject
	private CssClassBO bo;
	
	@Override
	protected Class<CssClass> getEntityClass() {
		return CssClass.class;
	}

	@Override
	protected ITGenericBO<CssClass> getBusinessObject() {
		return bo;
	}

	
	@Override
	public Class<CssClassImport> getImportModelClass() {
		return CssClassImport.class;
	}

}
