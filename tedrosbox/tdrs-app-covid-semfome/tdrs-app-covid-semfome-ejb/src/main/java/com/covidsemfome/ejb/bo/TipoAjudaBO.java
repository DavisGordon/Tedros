/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.TipoAjudaEAO;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TipoAjudaBO extends TGenericBO<TipoAjuda> {

	@Inject
	private TipoAjudaEAO eao;
	
	@Override
	public ITGenericEAO<TipoAjuda> getEao() {
		return eao;
	}
	

}
