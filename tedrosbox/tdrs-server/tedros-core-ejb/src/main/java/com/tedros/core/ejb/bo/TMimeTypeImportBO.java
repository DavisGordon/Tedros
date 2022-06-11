
package com.tedros.core.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.common.model.TMimeType;
import com.tedros.common.model.TMimeTypeImport;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TMimeTypeImportBO extends TImportFileEntityBO<TMimeType> {

	@Inject
	private TCoreBO<TMimeType> bo;
	
	@Override
	protected Class<TMimeType> getEntityClass() {
		return TMimeType.class;
	}

	@Override
	protected ITGenericBO<TMimeType> getBusinessObject() {
		return bo;
	}

	
	@Override
	public Class<TMimeTypeImport> getImportModelClass() {
		return TMimeTypeImport.class;
	}

}
