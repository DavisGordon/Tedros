/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.parceiro.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.parceiro.model.CssClass;
import com.covidsemfome.server.parceiro.eao.CssClassEAO;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CssClassBO extends TGenericBO<CssClass> {

	@Inject
	private CssClassEAO eao;
	
	@Override
	public CssClassEAO getEao() {
		return eao;
	}
	
	
}
