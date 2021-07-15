/**
 * 
 */
package com.solidarity.ejb.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.solidarity.model.Cozinha;
import com.solidarity.model.EstoqueConfig;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EstoqueConfigEAO extends TGenericEAO<EstoqueConfig> {

	@SuppressWarnings("unchecked")
	public List<EstoqueConfig> list(Cozinha cozinha){
		
		StringBuffer sbf = new StringBuffer("select e from EstoqueConfig e where e.cozinha.id = :id order by e.produto.nome");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", cozinha.getId());
		List<EstoqueConfig> lst = qry.getResultList();
		
		return (lst!=null && !lst.isEmpty()) ? lst : null;
	}
}
