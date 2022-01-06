/**
 * 
 */
package com.covidsemfome.server.estoque.eao;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Estocavel;
import com.covidsemfome.model.EstocavelItem;
import com.covidsemfome.model.Saida;
import com.covidsemfome.model.SaidaItem;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class SaidaEAO extends TGenericEAO<Saida> {

	private Comparator<EstocavelItem> comparator = (o1, o2) -> {
		return o1.getProduto().getNome().compareToIgnoreCase(o2.getProduto().getNome());
	};

	@SuppressWarnings("unchecked")
	public List<Saida> pesquisar(List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String orderby, String ordertype){
		
		StringBuffer sbf = new StringBuffer("select e from Saida e join e.acao a where 1=1 ");
		
		if(idsl!=null && idsl.size()>0)
			sbf.append("and e.id in :ids ");

		if(coz != null)
			sbf.append("and e.cozinha.id = :id ");
		
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
		
		
		if(StringUtils.isNotBlank(orderby)) {
			sbf.append("order by "+orderby);
			if(StringUtils.isNotBlank(ordertype))
				sbf.append(" "+ordertype);
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(idsl!=null && idsl.size()>0)
			qry.setParameter("ids", idsl);
		
		if(coz!=null)
			qry.setParameter("id", coz.getId());
		
		if(dataInicio!=null && dataFim!=null){
			qry.setParameter("dataInicio", dataInicio);
			qry.setParameter("dataFim", dataFim);
		}
			
		
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		List<Saida> lst = qry.getResultList();
		sortItens(lst);
		return lst;
	}
	
	private void sortItens(List<? extends Estocavel> lst) {
		if(lst!=null)
			for (Estocavel e : lst) 
				sortItens(e);
	}

	private void sortItens(Estocavel e) {
		if(e!=null && e.getItens()!=null) 
			Collections.sort(e.getItens(), comparator);
	}
	
	
	@Override
	public void beforePersist(Saida entidade)	throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeMerge(Saida entidade) throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeRemove(Saida entidade) throws Exception {
		childsReference(entidade);
	}
	
	
	public void childsReference(Saida entidade)throws Exception {
		
		if(entidade.getItens()!=null){
			for(final SaidaItem e : entidade.getItens())
				e.setSaida(entidade);
		}
		
	}
}
