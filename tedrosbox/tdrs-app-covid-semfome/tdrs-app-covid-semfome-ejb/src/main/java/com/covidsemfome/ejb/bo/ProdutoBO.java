/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.ProdutoEAO;
import com.covidsemfome.model.Produto;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ProdutoBO extends TGenericBO<Produto> {

	@Inject
	private ProdutoEAO eao;
	
	@Override
	public ProdutoEAO getEao() {
		return eao;
	}
	

}
