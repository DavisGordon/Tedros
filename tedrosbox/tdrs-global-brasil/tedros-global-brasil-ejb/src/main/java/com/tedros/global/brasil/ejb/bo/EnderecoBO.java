/**
O * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.bo;

import javax.persistence.EntityManager;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.global.brasil.ejb.eao.EnderecoEAO;
import com.tedros.global.brasil.model.Endereco;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class EnderecoBO extends TGenericBO<Endereco> {

	private EnderecoEAO eao = new EnderecoEAO();
	
	@Override
	public ITGenericEAO<Endereco> getEao() {
		return eao;
	}
	
	public void excluirTodos(EntityManager em, final Long idPessoa)throws Exception{
		eao.excluirTodos(em, idPessoa);
	}

}
