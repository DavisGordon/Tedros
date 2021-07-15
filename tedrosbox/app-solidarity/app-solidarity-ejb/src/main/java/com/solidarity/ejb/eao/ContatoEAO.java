/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.solidarity.model.Contato;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ContatoEAO extends TGenericEAO<Contato> {

	public void excluirTodos(final Long idPessoa)throws Exception{
		Query qry = getEntityManager().createQuery("delete from Contato where pessoa.id = :id");
		qry.setParameter("id", idPessoa);
		qry.executeUpdate();
	}
	
}
