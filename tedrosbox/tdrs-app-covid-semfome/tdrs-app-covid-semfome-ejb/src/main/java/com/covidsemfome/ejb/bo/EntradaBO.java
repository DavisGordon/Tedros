/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.EntradaEAO;
import com.covidsemfome.model.Entrada;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EntradaBO extends TGenericBO<Entrada> {

	@Inject
	private EntradaEAO eao;
	
	@Override
	public EntradaEAO getEao() {
		return eao;
	}
	

}
