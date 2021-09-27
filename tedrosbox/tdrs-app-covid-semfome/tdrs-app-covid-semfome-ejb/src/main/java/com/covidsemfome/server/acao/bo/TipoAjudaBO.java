/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.acao.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.server.acao.eao.TipoAjudaEAO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TipoAjudaBO extends TGenericBO<TipoAjuda> {

	@Inject
	private TipoAjudaEAO eao;
	
	@Override
	public ITGenericEAO<TipoAjuda> getEao() {
		return eao;
	}
	
	public List<TipoAjuda> listar(String tipo){
		return eao.listar(tipo);
	}

}
