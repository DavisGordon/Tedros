/**
 * 
 */
package com.covidsemfome.server.estoque.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.EstoqueConfig;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EstoqueConfigEAO extends TGenericEAO<EstoqueConfig> {

	@SuppressWarnings("unchecked")
	public List<EstoqueConfig> list(Cozinha cozinha){
		
		StringBuffer sbf = new StringBuffer("select e from EstoqueConfig e where 1=1 ");
		
		if(cozinha!=null)
			sbf.append("and e.cozinha.id = :id ");
		
		sbf.append("order by e.produto.nome");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(cozinha!=null)
			qry.setParameter("id", cozinha.getId());
		
		List<EstoqueConfig> lst = qry.getResultList();
		
		return (lst!=null && !lst.isEmpty()) ? lst : null;
	}
}
