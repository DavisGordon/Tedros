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
	public List<AjudaCampanha> recuperar(Campanha c, FormaAjuda fa, String p, Integer diasAtras){
		StringBuffer sbf = new StringBuffer("select distinct e from AjudaCampanha e "
				+ "join e.campanha c "
				+ "left join e.formaAjuda fa "
				+ "where c.status='ATIVADO' "
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
		if(p!=null && diasAtras!=null) {
			sbf.append("and (e.periodo = :p and ( ");
			sbf.append("FUNC('datediff', 'DAY', e.dataProcessado, CURRENT_DATE) > :ini ");
			sbf.append("and FUNC('datediff', 'DAY', e.dataProcessado, CURRENT_DATE) <= :fim)) ");
		}else 
			sbf.append("and e.dataProcessado is null ");
			
		//datediff(DAY, ac.insert_date, current_date) > 15
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		 
		if(c!=null)
			qry.setParameter("cId", c.getId());
		if(fa!=null)
			qry.setParameter("faId", fa.getId());
		if(p!=null && diasAtras!=null) {
			qry.setParameter("p", p);
			qry.setParameter("ini", diasAtras-1);
			qry.setParameter("fim", diasAtras+2);
		}
		
		
		return qry.getResultList();
	}
	
	
}
