/**
 * 
 */
package com.covidsemfome.ejb.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Estoque;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EstoqueEAO extends TGenericEAO<Estoque> {

	@SuppressWarnings("unchecked")
	public Estoque getUltimo(Cozinha cozinha) {
		StringBuffer sbf = new StringBuffer("select e from Estoque e where e.cozinha.id = :id order by e.id desc");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", cozinha.getId());
		List<Estoque> lst = qry.setMaxResults(1).getResultList();
		
		return (lst!=null && !lst.isEmpty()) ? lst.get(0) : null;
 	}
	
	@SuppressWarnings("unchecked")
	public Estoque getAnterior(Long id, Cozinha cozinha) {
		StringBuffer sbf = new StringBuffer("select e from Estoque e where e.id < :id and e.cozinha.id = :idCoz order by e.id desc");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", id);
		qry.setParameter("idCoz", cozinha.getId());
		List<Estoque> lst = qry.setMaxResults(1).getResultList();
		
		return (lst!=null && !lst.isEmpty()) ? lst.get(0) : null;
 	}
	
	@SuppressWarnings("unchecked")
	public List<Estoque> getPosteriores(Long id, Cozinha cozinha) {
		StringBuffer sbf = new StringBuffer("select e from Estoque e where e.id > :id and e.cozinha.id = :idCoz");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", id);
		qry.setParameter("idCoz", cozinha.getId());
		List<Estoque> lst = qry.getResultList();
		
		return lst;
 	}
}
