/**
 * 
 */
package com.covidsemfome.ejb.eao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class VoluntarioEAO extends TGenericEAO<Voluntario> {


	@SuppressWarnings("unchecked")
	public List<Voluntario> pesquisar(List<Long> idsl, String titulo, String status, String nome, List<Long> idsTipAj,
			Date dataInicio, Date dataFim, String orderby, String ordertype){
	
		StringBuffer sbf = new StringBuffer("select distinct e from Voluntario e join e.acao a join e.pessoa p join e.tiposAjuda t where 1=1 ");
		
		if(idsl!=null)
			sbf.append("and a.id in :ids ");

		if(idsTipAj!=null)
			sbf.append("and t.id in :idsTipAj ");
		
		if(StringUtils.isNotBlank(titulo))
			sbf.append("and lower(a.titulo) like :titulo ");
		
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
			sbf.append("and a.data >= :dataInicio and a.data <= :dataFim ");
		
		if(StringUtils.isNotBlank(status))
			sbf.append("and a.status = :status ");
		
		if(StringUtils.isNotBlank(nome))
			sbf.append("and lower(p.nome) like :nome ");
		
		if(StringUtils.isNotBlank(orderby)) {
			sbf.append("order by "+orderby);
			if(StringUtils.isNotBlank(ordertype))
				sbf.append(" "+ordertype);
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(idsl!=null)
			qry.setParameter("ids", idsl);
		
		if(idsTipAj!=null)
			qry.setParameter("idsTipAj", idsTipAj);
		
		if(StringUtils.isNotBlank(titulo))
			qry.setParameter("titulo", "%"+titulo.toLowerCase()+"%");
		
		if(dataInicio!=null && dataFim!=null){
			qry.setParameter("dataInicio", dataInicio);
			qry.setParameter("dataFim", dataFim);
		}
		
		if(StringUtils.isNotBlank(status))
			qry.setParameter("status", status);
		
		if(StringUtils.isNotBlank(nome))
			qry.setParameter("nome", "%"+nome.toLowerCase()+"%");
		
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		List<Voluntario> lst = qry.getResultList();
		return lst;
	}
	
	
	public Voluntario recuperar(Acao acao, Pessoa pess){
		StringBuffer sbf = new StringBuffer("select distinct e from Voluntario e  where e.acao.id = :acao and e.pessoa.id = :pess");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("acao", acao.getId());
		qry.setParameter("pess", pess.getId());
		
		return (Voluntario) qry.getSingleResult();
	}
	
	public boolean isVoluntario(Pessoa pess){
		StringBuffer sbf = new StringBuffer("select count(e) from Voluntario e where e.pessoa.id = :pess");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("pess", pess.getId());
		
		return ((Long) qry.getSingleResult()) > 0;
	}
	
	@Override
	public void afterRemove(Voluntario entity) throws Exception {
		flush();
	}

	@Override
	public void afterPersist(Voluntario entity) throws Exception {
		flush();
	}
	
	@Override
	public void afterMerge(Voluntario entity) throws Exception {
		flush();
	}
	
	private void flush() {
		super.getEntityManager().flush();
	}
	
	
}
