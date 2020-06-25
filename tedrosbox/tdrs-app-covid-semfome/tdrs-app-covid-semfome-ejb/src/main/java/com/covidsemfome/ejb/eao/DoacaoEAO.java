/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.covidsemfome.model.Doacao;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DoacaoEAO extends TGenericEAO<Doacao> {

	public void excluirTodos(final Long idPessoa)throws Exception{
		Query qry = getEntityManager().createQuery("delete from Documento where pessoa.id = :id");
		qry.setParameter("id", idPessoa);
		qry.executeUpdate();
	}
	
}
