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
import com.covidsemfome.model.Estoque;
import com.covidsemfome.model.EstoqueItem;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EstoqueEAO extends TGenericEAO<Estoque> {
	
	private Comparator<EstoqueItem> comparator = (o1, o2) -> {
			return o1.getProduto().getNome().compareToIgnoreCase(o2.getProduto().getNome());
		};
		
	@Override
	public void afterFind(Estoque e) throws Exception {
		sortItens(e);
	}
	
	@Override
	public void afterFindAll(List<Estoque> lst) throws Exception {
		sortItens(lst);
	}
	
	@Override
	public void afterListAll(List<Estoque> lst) throws Exception {
		sortItens(lst);
	}
	
	@Override
	public void afterPageAll(List<Estoque> lst) throws Exception {
		sortItens(lst);
	}

	private void sortItens(List<Estoque> lst) {
		if(lst!=null)
			for (Estoque e : lst) 
				sortItens(e);
	}

	private void sortItens(Estoque e) {
		if(e!=null && e.getItens()!=null) 
			Collections.sort(e.getItens(), comparator);
	}

	@SuppressWarnings("unchecked")
	public List<Estoque> pesquisar(List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String origem, String orderby, String ordertype){
		
		StringBuffer sbf = new StringBuffer("select e from Estoque e where 1=1 ");
		
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
		
		if(StringUtils.isNotBlank(origem))
			if(origem.equals("E"))
				sbf.append("and e.entradaRef is not null ");
			else
				sbf.append("and e.producaoRef is not null ");
		
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
		
		qry.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
		List<Estoque> lst = qry.getResultList();
		sortItens(lst);
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public Estoque getUltimo(Cozinha cozinha, Date data) {
		StringBuffer sbf = new StringBuffer("select e from Estoque e "
				+ "where e.cozinha.id = :id "
				+ "and e.data < :data "
				+ "order by e.data desc");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", cozinha.getId());
		qry.setParameter("data", data);
		List<Estoque> lst = qry.setMaxResults(1).getResultList();
		
		return (lst!=null && !lst.isEmpty()) ? lst.get(0) : null;
 	}
	
	@SuppressWarnings("unchecked")
	public Estoque getAnterior(Long id, Cozinha cozinha) {
		StringBuffer sbf = new StringBuffer("select e from Estoque e "
				+ "where e.data < (select e1.data from Estoque e1 where e1.id = :id and e1.cozinha.id = e.cozinha.id) "
				+ "and e.cozinha.id = :idCoz order by e.data desc");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", id);
		qry.setParameter("idCoz", cozinha.getId());
		List<Estoque> lst = qry.setMaxResults(1).getResultList();
		
		return (lst!=null && !lst.isEmpty()) ? lst.get(0) : null;
 	}
	
	@SuppressWarnings("unchecked")
	public List<Estoque> getPosteriores(Long id, Cozinha cozinha) {
		StringBuffer sbf = new StringBuffer("select e from Estoque e "
				+ "where e.data > (select e1.data from Estoque e1 where e1.id = :id and e1.cozinha.id = e.cozinha.id) "
				+ "and e.cozinha.id = :idCoz order by e.data");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", id);
		qry.setParameter("idCoz", cozinha.getId());
		List<Estoque> lst = qry.getResultList();
		
		return lst;
 	}
	
	@SuppressWarnings("unchecked")
	public List<Estoque> getPosteriores(Cozinha cozinha, Date data) {
		StringBuffer sbf = new StringBuffer("select e from Estoque e "
				+ "where e.data > :data "
				+ "and e.cozinha.id = :idCoz order by e.data");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("data", data);
		qry.setParameter("idCoz", cozinha.getId());
		List<Estoque> lst = qry.getResultList();
		
		return lst;
 	}
}
