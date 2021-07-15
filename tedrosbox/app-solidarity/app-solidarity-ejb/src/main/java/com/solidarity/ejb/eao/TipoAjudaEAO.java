/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.solidarity.model.TipoAjuda;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TipoAjudaEAO extends TGenericEAO<TipoAjuda> {

	public List<TipoAjuda> listar(String tipo){
		StringBuffer sbf = new StringBuffer("select distinct e from TipoAjuda e where e.status = :status and e.tipoPessoa = :tipo order by e.id ");
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("tipo", tipo);
		qry.setParameter("status", "ATIVADO");
		List<TipoAjuda> lst = qry.getResultList();
		return lst;
	}
	
}
