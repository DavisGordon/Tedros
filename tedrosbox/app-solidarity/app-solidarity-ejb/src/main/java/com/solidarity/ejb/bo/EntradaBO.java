/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.solidarity.ejb.eao.EntradaEAO;
import com.solidarity.model.Entrada;
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
