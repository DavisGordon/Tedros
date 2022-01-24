/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.pessoa.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.Contato;
import org.somos.server.pessoa.eao.ContatoEAO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;



/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ContatoBO extends TGenericBO<Contato> {

	@Inject
	private ContatoEAO eao;
	
	@Override
	public ITGenericEAO<Contato> getEao() {
		return eao;
	}
	
	public void excluirTodos(final Long idPessoa)throws Exception{
		eao.excluirTodos(idPessoa);
	}

}
