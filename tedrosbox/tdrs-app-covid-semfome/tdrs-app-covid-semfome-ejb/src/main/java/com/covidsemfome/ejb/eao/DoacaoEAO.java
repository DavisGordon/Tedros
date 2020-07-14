/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.eao;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DoacaoEAO extends TGenericEAO<Doacao> {

	public List<Doacao> pesquisar(String nome, Date dataInicio, Date dataFim, Long acaoId, TipoAjuda tipoAjuda){
		Query qry = getEntityManager().createQuery("delete from Documento where pessoa.id = :id");
		//qry.setParameter("id", idPessoa);
		return null;
	}
	
}
