/**
 * 
 */
package org.somos.server.parceiro.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.parceiro.model.CssClass;
import org.somos.server.parceiro.bo.CssClassImportBO;

import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.ejb.base.service.TEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="CssClassImportService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CssClassImportService extends TEjbImportService<CssClass> {

	@Inject
	private CssClassImportBO bo;
	
	@Override
	public TImportFileEntityBO<CssClass> getBusinessObject() {
		return bo;
	}

}
