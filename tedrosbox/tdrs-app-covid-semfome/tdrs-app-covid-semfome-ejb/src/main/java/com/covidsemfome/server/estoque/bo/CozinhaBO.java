/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.server.estoque.eao.CozinhaEAO;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CozinhaBO extends TGenericBO<Cozinha> {

	@Inject
	private CozinhaEAO eao;
	
	@Override
	public CozinhaEAO getEao() {
		return eao;
	}
	

}
