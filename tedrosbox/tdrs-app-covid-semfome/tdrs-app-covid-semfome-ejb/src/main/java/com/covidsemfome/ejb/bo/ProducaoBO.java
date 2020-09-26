/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.ProducaoEAO;
import com.covidsemfome.model.Producao;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ProducaoBO extends TGenericBO<Producao> {

	@Inject
	private ProducaoEAO eao;
	
	@Override
	public ProducaoEAO getEao() {
		return eao;
	}
	

}
