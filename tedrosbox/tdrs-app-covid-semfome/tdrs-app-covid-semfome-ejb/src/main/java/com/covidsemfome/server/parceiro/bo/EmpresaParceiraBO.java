/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.parceiro.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.parceiro.model.EmpresaParceira;
import com.covidsemfome.server.parceiro.eao.EmpresaParceiraEAO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;



/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmpresaParceiraBO extends TGenericBO<EmpresaParceira> {

	@Inject
	private EmpresaParceiraEAO eao;
	
	@Override
	public ITGenericEAO<EmpresaParceira> getEao() {
		return eao;
	}


}
