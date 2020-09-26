/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.EstoqueEAO;
import com.covidsemfome.model.Estoque;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EstoqueBO extends TGenericBO<Estoque> {

	@Inject
	private EstoqueEAO eao;
	
	@Override
	public EstoqueEAO getEao() {
		return eao;
	}
	

}
