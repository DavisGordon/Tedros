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
import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.EntradaItem;
import com.covidsemfome.model.Estocavel;
import com.covidsemfome.model.EstocavelItem;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EntradaEAO extends TGenericEAO<Entrada> {

	private Comparator<EstocavelItem> comparator = (o1, o2) -> {
		return o1.getProduto().getNome().compareToIgnoreCase(o2.getProduto().getNome());
	};

	@SuppressWarnings("unchecked")
	public List<Entrada> pesquisar(List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String tipo, String orderby, String ordertype){
		
		StringBuffer sbf = new StringBuffer("select e from Entrada e where 1=1 ");
		
		if(idsl!=null)
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
		
		if(StringUtils.isNotBlank(tipo))
			sbf.append("and e.tipo = :tipo");
		
		if(StringUtils.isNotBlank(orderby)) {
			sbf.append("order by e."+orderby);
			if(StringUtils.isNotBlank(ordertype))
				sbf.append(" "+ordertype);
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(idsl!=null)
			qry.setParameter("ids", idsl);
		
		if(coz!=null)
			qry.setParameter("id", coz.getId());
		
		if(dataInicio!=null && dataFim!=null){
			qry.setParameter("dataInicio", dataInicio);
			qry.setParameter("dataFim", dataFim);
		}
		
		if(StringUtils.isNotBlank(tipo))
			qry.setParameter("tipo", tipo);
			
		
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		List<Entrada> lst = qry.getResultList();
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
	public void beforePersist(Entrada entidade)	throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeMerge(Entrada entidade) throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeRemove(Entrada entidade) throws Exception {
		childsReference(entidade);
	}
	
	
	public void childsReference(Entrada entidade)throws Exception {
		
		if(entidade.getItens()!=null){
			for(final EntradaItem e : entidade.getItens())
				e.setEntrada(entidade);
		}
		
	}
	
}
