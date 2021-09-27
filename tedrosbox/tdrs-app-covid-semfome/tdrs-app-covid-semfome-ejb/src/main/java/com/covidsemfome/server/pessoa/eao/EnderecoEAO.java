/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.pessoa.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.covidsemfome.domain.DomainSchema;
import com.covidsemfome.domain.DomainTables;
import com.covidsemfome.model.Endereco;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EnderecoEAO extends TGenericEAO<Endereco> {

	private static String SCHEMA = DomainSchema.riosemfome;
	
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
