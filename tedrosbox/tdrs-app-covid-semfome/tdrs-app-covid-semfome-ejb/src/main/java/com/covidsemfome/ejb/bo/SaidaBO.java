/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.SaidaEAO;
import com.covidsemfome.model.Saida;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class SaidaBO extends TGenericBO<Saida> {

	@Inject
	private SaidaEAO eao;
	
	@Override
	public SaidaEAO getEao() {
		return eao;
	}
	

}
