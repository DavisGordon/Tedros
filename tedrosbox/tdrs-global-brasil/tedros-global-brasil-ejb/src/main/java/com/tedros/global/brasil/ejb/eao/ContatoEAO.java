/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.eao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.global.brasil.model.Contato;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class ContatoEAO extends TGenericEAO<Contato> {

	public void excluirTodos(EntityManager em, final Long idPessoa)throws Exception{
		Query qry = em.createQuery("delete from Contato where pessoa.id = :id");
		qry.setParameter("id", idPessoa);
		qry.executeUpdate();
	}
	
}
