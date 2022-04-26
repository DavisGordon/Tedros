/**
 * 
 */
package org.somos.server.campanha.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.CampanhaMailConfig;
import org.somos.server.campanha.eao.CampanhaMailConfigEAO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CampanhaMailConfigBO extends TGenericBO<CampanhaMailConfig> {

	@Inject
	private CampanhaMailConfigEAO eao;
	
	@Override
	public ITGenericEAO<CampanhaMailConfig> getEao() {
		return eao;
	}

}
