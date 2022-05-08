/**
 * 
 */
package org.somos.server.campanha.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.somos.model.AjudaCampanha;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;

import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AjudaCampanhaEAO extends TGenericEAO<AjudaCampanha> {

	@SuppressWarnings("unchecked")
	public List<AjudaCampanha> listarParaProcessamento(Campanha c, FormaAjuda fa){
		StringBuffer sbf = new StringBuffer("select distinct e from AjudaCampanha e "
				+ "join e.campanha c "
				+ "left join e.formaAjuda fa "
				+ "where c.status='ATIVADO' "
				//+ "and (fa.tercerizado is null or fa.tercerizado = 'Não' )"
				+ "and (c.dataFim is null or c.dataFim >= SQL('CURRENT_DATE')) ");
		
		if(fa!=null && c==null) {
			sbf.append("and fa.id = :faId ");
			sbf.append("and not exists (select 1 from CampanhaMailConfig m where m.campanha.id = c.id) ");
		}else {
			if(c!=null)
				sbf.append("and c.id = :cId ");
			if(fa!=null)
				sbf.append("and fa.id = :faId ");
		}
		
		sbf.append("and e.dataProximo <= CURRENT_DATE ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		 
		if(c!=null)
			qry.setParameter("cId", c.getId());
		if(fa!=null)
			qry.setParameter("faId", fa.getId());
		
		return qry.getResultList();
	}
	
	
}
