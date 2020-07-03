/**
 * 
 */
package com.covidsemfome.ejb.eao;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AcaoEAO extends TGenericEAO<Acao> {

	@Override
	public List<Acao> listAll(Class<? extends ITEntity> entity) throws Exception {
		List<Acao> lst = super.listAll(entity);
		return refresh(lst); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Acao> listAcoes(Date data){
		StringBuffer sbf = new StringBuffer("select distinct e from Acao e left join fetch e.voluntarios v where e.data >= :data order by e.data ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("data", data);
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		List<Acao> lst = qry.getResultList();
		return refresh(lst);
	}

	private List<Acao> refresh(List<Acao> lst) {
		if(lst!=null)
			for (Acao acao : lst) {
				getEntityManager().refresh(acao);
			}
		return lst;
	}
}
