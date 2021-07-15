/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.solidarity.ejb.eao.ProdutoEAO;
import com.solidarity.model.Produto;
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
