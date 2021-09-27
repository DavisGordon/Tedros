/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.pessoa.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.model.UF;
import com.covidsemfome.server.pessoa.eao.UFEAO;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class UFBO extends TGenericBO<UF> {

	@Inject
	private UFEAO eao;
	
	@Override
	public UFEAO getEao() {
		return eao;
	}
	

}
