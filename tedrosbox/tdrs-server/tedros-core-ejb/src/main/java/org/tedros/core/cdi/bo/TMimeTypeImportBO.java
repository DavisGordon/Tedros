
package org.tedros.core.cdi.bo;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import org.tedros.common.model.TMimeType;
import org.tedros.common.model.TMimeTypeImport;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;

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
