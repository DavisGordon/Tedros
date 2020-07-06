/**
 * 
 */
package com.covidsemfome.ejb.eao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Mailing;
import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class MailingEAO extends TGenericEAO<Mailing> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Mailing> listAll(Class<? extends ITEntity> entity) throws Exception {
		StringBuffer sbf = new StringBuffer("select e from Acao e left join fetch e.voluntarios v  order by e.data desc ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		
		List<Mailing> res = new ArrayList<>();
		List<Acao> lst = qry.getResultList();
		if(lst!=null)
			for (Acao e : lst) {
				getEntityManager().refresh(e);
				res.add(new Mailing(e.getId(), e.getVersionNum(), e.getLastUpdate(), e.getInsertDate(), 
						 e.getTitulo(), e.getDescricao(), e.getData(), e.getStatus(), e.getObservacao(), e.getQtdMinVoluntarios(), 
						 e.getQtdMaxVoluntarios(), e.getVlrPrevisto(), e.getVlrArrecadado(), e.getVlrExecutado(), e.getVoluntarios()));
			}
		
		return res;
	}
	
	
}
