/**
 * 
 */
package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.common.model.TMimeType;
import com.tedros.core.ejb.bo.TMimeTypeImportBO;
import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.ejb.base.service.TEjbImportService;

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
