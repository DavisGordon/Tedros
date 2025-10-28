/**
 * 
 */
package org.tedros.core.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.common.model.TMimeType;
import org.tedros.core.cdi.bo.TMimeTypeImportBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;
import org.tedros.server.ejb.service.TEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="TMimeTypeImportService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TMimeTypeImportService extends TEjbImportService<TMimeType> {

	@Inject
	private TMimeTypeImportBO bo;
	
	@Override
	public TImportFileEntityBO<TMimeType> getBusinessObject() {
		return bo;
	}

}
