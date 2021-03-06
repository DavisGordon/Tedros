/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.solidarity.ejb.eao.EstoqueConfigEAO;
import com.solidarity.model.Cozinha;
import com.solidarity.model.EstoqueConfig;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EstoqueConfigBO extends TGenericBO<EstoqueConfig> {

	@Inject
	private EstoqueConfigEAO eao;
	
	@Override
	public EstoqueConfigEAO getEao() {
		return eao;
	}
	
	public List<EstoqueConfig> list(Cozinha coz){
		return eao.list(coz);
	}

}
