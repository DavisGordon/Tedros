/**
 * 
 */
package org.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.cdi.bo.TMimeTypeImportBO;
import org.tedros.server.cdi.bo.TImportFileEntityBO;
import org.tedros.server.ejb.service.TEjbImportService;

import org.tedros.common.model.TMimeType;

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
