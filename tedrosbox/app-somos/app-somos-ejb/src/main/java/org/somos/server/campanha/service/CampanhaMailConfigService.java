/**
 * 
 */
package org.somos.server.campanha.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.AjudaCampanha;
import org.somos.model.CampanhaMailConfig;
import org.somos.server.campanha.bo.CampanhaMailConfigBO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="CampanhaMailConfigService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CampanhaMailConfigService extends TEjbService<CampanhaMailConfig>  {

	@Inject
	private CampanhaMailConfigBO bo;
	
	@Override
	public TGenericBO<CampanhaMailConfig> getBussinesObject() {
		return bo;
	}
	
	public String prepareContent(CampanhaMailConfig cmc, AjudaCampanha ac) {
		return bo.prepareContent(cmc, ac);
	}
	
}
