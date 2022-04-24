/**
 * 
 */
package org.somos.server.campanha.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.Campanha;
import org.somos.server.campanha.eao.CampanhaEAO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CampanhaBO extends TGenericBO<Campanha> {

	@Inject
	private CampanhaEAO eao;
	
	@Override
	public ITGenericEAO<Campanha> getEao() {
		return eao;
	}

}
