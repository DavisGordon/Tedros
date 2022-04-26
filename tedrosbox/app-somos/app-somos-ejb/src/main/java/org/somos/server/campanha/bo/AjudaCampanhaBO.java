/**
 * 
 */
package org.somos.server.campanha.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.AjudaCampanha;
import org.somos.server.campanha.eao.AjudaCampanhaEAO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AjudaCampanhaBO extends TGenericBO<AjudaCampanha> {

	@Inject
	private AjudaCampanhaEAO eao;
	
	@Override
	public ITGenericEAO<AjudaCampanha> getEao() {
		return eao;
	}

}
