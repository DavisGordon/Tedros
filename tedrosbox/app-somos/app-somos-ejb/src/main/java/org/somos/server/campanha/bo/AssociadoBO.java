/**
 * 
 */
package org.somos.server.campanha.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.Associado;
import org.somos.model.Pessoa;
import org.somos.server.campanha.eao.AssociadoEAO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AssociadoBO extends TGenericBO<Associado> {

	@Inject
	private AssociadoEAO eao;
	
	@Override
	public ITGenericEAO<Associado> getEao() {
		return eao;
	}
	
	public Associado recuperar(Pessoa p){
		return eao.recuperar(p);
	}

}
