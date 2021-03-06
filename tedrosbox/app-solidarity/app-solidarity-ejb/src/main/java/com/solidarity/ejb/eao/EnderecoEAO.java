/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.solidarity.domain.DomainSchema;
import com.solidarity.domain.DomainTables;
import com.solidarity.model.Endereco;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EnderecoEAO extends TGenericEAO<Endereco> {

	private static String SCHEMA = DomainSchema.solidarity;
	
	public void excluirTodos(final Long idPessoa)throws Exception{
	
		Query qry = getEntityManager().createNativeQuery("delete from "+SCHEMA+"."+DomainTables.endereco+
				" where id in (select ende_id from "+SCHEMA+"."+DomainTables.pessoa_endereco+" where pess_id = "+idPessoa+" )");
		//qry.setParameter("id", idPessoa);
		qry.executeUpdate();
		
		qry = getEntityManager().createNativeQuery("delete from "+SCHEMA+"."+DomainTables.pessoa_endereco+
				" where pess_id = "+idPessoa);
		//qry.setParameter("id", idPessoa);
		qry.executeUpdate();
	}
	
}
