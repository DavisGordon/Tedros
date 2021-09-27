/**
 * 
 */
package com.covidsemfome.server.estoque.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.covidsemfome.model.Produto;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ProdutoEAO extends TGenericEAO<Produto> {


	@SuppressWarnings("unchecked")
	public List<Produto> pesquisar(List<String> cod, String nome, String marca, String medida, String uniMed, String orderby, String ordertype){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Produto e where 1=1 ");
		
		if(StringUtils.isNotBlank(nome))
			sbf.append("and lower(e.nome) like :nome ");
		
		if(cod!=null)
			sbf.append("and e.codigo in :cod ");
		
		if(StringUtils.isNotBlank(marca))
			sbf.append("and lower(e.marca) like :marca ");
		
		if(StringUtils.isNotBlank(medida))
			sbf.append("and e.medida = :medida ");
		
		if(StringUtils.isNotBlank(uniMed))
			sbf.append("and e.unidadeMedida = :um ");
		
		if(StringUtils.isNotBlank(orderby)) {
			sbf.append("order by e."+orderby);
			if(StringUtils.isNotBlank(ordertype))
				sbf.append(" "+ordertype);
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(nome))
			qry.setParameter("nome", "%"+nome.toLowerCase()+"%");
		
		if(cod!=null)
			qry.setParameter("cod", cod);
		
		if(StringUtils.isNotBlank(marca))
			qry.setParameter("marca", "%"+marca.toLowerCase()+"%");
		
		if(StringUtils.isNotBlank(medida))
			qry.setParameter("medida", medida);
		
		if(StringUtils.isNotBlank(uniMed))
			qry.setParameter("um", uniMed);
		
		return qry.getResultList();
	}
	
}
