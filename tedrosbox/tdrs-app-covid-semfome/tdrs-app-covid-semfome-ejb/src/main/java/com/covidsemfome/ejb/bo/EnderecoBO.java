/**
O * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.EnderecoEAO;
import com.covidsemfome.model.Endereco;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EnderecoBO extends TGenericBO<Endereco> {

	@Inject
	private EnderecoEAO eao;
	
	@Override
	public ITGenericEAO<Endereco> getEao() {
		return eao;
	}
	
	public void excluirTodos(final Long idPessoa)throws Exception{
		eao.excluirTodos(idPessoa);
	}

}
