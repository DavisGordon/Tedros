/**
 * 
 */
package com.covidsemfome.ejb.eao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AcaoEAO extends TGenericEAO<Acao> {
	
	@SuppressWarnings("unchecked")
	public List<Acao> pesquisar(List<Long> idsl, String titulo, Date dataInicio, Date dataFim, String status){
	
		StringBuffer sbf = new StringBuffer("select e from Acao e where 1=1 ");
		
		if(idsl!=null)
			sbf.append("and e.id in :ids ");

		
		if(StringUtils.isNotBlank(titulo))
			sbf.append("and lower(e.titulo) like :titulo ");
		
		if(dataInicio!=null && dataFim==null) {
			Calendar c = Calendar.getInstance();
			c.setTime(dataInicio);
			c.set(Calendar.HOUR, 23);
			c.set(Calendar.MINUTE, 59);
			dataFim = c.getTime();
		}else if(dataInicio!=null && dataFim!=null) {
			Calendar c = Calendar.getInstance();
			c.setTime(dataFim);
			c.set(Calendar.HOUR, 23);
			c.set(Calendar.MINUTE, 59);
			dataFim = c.getTime();
		}
		
		if(dataInicio!=null && dataFim!=null)
			sbf.append("and e.data >= :dataInicio and e.data <= :dataFim ");
		
		if(StringUtils.isNotBlank(status))
			sbf.append("and e.status = :status ");
		
		sbf.append("order by e.data desc ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(idsl!=null)
			qry.setParameter("ids", idsl);
		
		if(StringUtils.isNotBlank(titulo))
			qry.setParameter("titulo", "%"+titulo.toLowerCase()+"%");
		
		if(dataInicio!=null && dataFim!=null){
			qry.setParameter("dataInicio", dataInicio);
			qry.setParameter("dataFim", dataFim);
		}
		
		if(StringUtils.isNotBlank(status))
			qry.setParameter("status", status);
		
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		List<Acao> lst = qry.getResultList();
		return lst;
	}
	
	@Override
	public List<Acao> listAll(Class<? extends ITEntity> entity) throws Exception {
		List<Acao> lst = super.listAll(entity, false);
		return lst; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Acao> listAcoes(Date data){
		StringBuffer sbf = new StringBuffer("select distinct e from Acao e left join fetch e.voluntarios v where e.data >= :data order by e.data ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("data", data);
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		List<Acao> lst = qry.getResultList();
		return lst;
	}

	@Override
	public void beforePersist(Acao entidade)	throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeMerge(Acao entidade) throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeRemove(Acao entidade) throws Exception {
		childsReference(entidade);
	}
	
	
	public void childsReference(Acao entidade)throws Exception {
		
		if(entidade.getVoluntarios()!=null){
			for(final Voluntario e : entidade.getVoluntarios())
				e.setAcao(entidade);
		}
	}

	
}
