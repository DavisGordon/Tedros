/**
 * 
 */
package com.solidarity.ejb.eao;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.QueryByExamplePolicy;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.queries.ReportQuery;
import org.eclipse.persistence.queries.ReportQueryResult;

import com.solidarity.model.Acao;
import com.solidarity.model.Mailing;
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
		StringBuffer sbf = new StringBuffer("select e from Acao e order by e.data desc ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		List<Acao> lst = qry.getResultList();
		
		return process(lst);
	}


	@SuppressWarnings("unchecked")
	public List<Mailing> pageAll(Mailing mailing, int firstResult, int maxResult, boolean orderByAsc)throws Exception{
		
		Acao entity = (Acao) mailing;
		
		CriteriaBuilder cb = super.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Acao> cq = (CriteriaQuery<Acao>) cb.createQuery(Acao.class);
		Root<Acao> root = (Root<Acao>) cq.from(Acao.class);
		cq.select(root);
		
		if(entity.getOrderBy()!=null && !entity.getOrderBy().isEmpty()) {
			for(String f : entity.getOrderBy())
				if(orderByAsc)
					cq.orderBy(cb.asc(root.get(f)));
				else
					cq.orderBy(cb.desc(root.get(f)));
		}
		
		TypedQuery<Acao> qry = super.getEntityManager().createQuery(cq);
		qry.setFirstResult(firstResult);
		qry.setMaxResults(maxResult);
		
		List<Acao> res = qry.getResultList();
		
		return process(res);
	}
	
	@SuppressWarnings("unchecked")
	public List<Mailing> findAll(Mailing mailing, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception{
		
		Acao entity = new Acao();
		entity.setTitulo(mailing.getTitulo());
		
		ReadAllQuery query = new ReadAllQuery(Acao.class);
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		
		policy.addSpecialOperation(String.class, (containsAnyKeyWords) ? "containsAnyKeyWords" : "like");
		
		if(entity.getOrderBy()!=null && !entity.getOrderBy().isEmpty()) {
			for(String f : entity.getOrderBy())
				if(orderByAsc)
					query.addAscendingOrdering(f);
				else
					query.addDescendingOrdering(f);
		}
		query.setQueryByExamplePolicy(policy);
		query.setFirstResult(firstResult);
		query.setMaxRows(maxResult+firstResult);
		
		List<Acao> res = (List<Acao>) ((JpaEntityManager) super.getEntityManager().getDelegate()).getActiveSession().executeQuery(query);
		
		return process(res);
	}
	
	public Integer countFindAll(Mailing mailing, boolean containsAnyKeyWords)throws Exception{
		
		Acao entity = new Acao();
		entity.setTitulo(mailing.getTitulo());
		
		ExpressionBuilder eb = new ExpressionBuilder();
		ReportQuery query = new ReportQuery(Acao.class, eb);
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, (containsAnyKeyWords) ? "containsAnyKeyWords" : "like");
		query.setQueryByExamplePolicy(policy);
		
		query.addCount();
		
		//List<E> lst = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		ReportQueryResult res = (ReportQueryResult) ((Vector)((JpaEntityManager) super.getEntityManager().getDelegate()).getActiveSession().executeQuery(query)).get(0);
		Integer total = (Integer) res.get("COUNT");
		return  total;
	}
	
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = super.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = (CriteriaQuery<Long>) cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Acao.class)));
		return (Long) super.getEntityManager().createQuery(cq).getSingleResult();
	}
	
	private List<Mailing> process(List<Acao> lst) {
		List<Mailing> res = new ArrayList<>();
		
		if(lst!=null)
			for (Acao e : lst) {
				res.add(new Mailing(e.getId(), e.getVersionNum(), e.getLastUpdate(), e.getInsertDate(), 
						 e.getTitulo(), e.getDescricao(), e.getData(), e.getStatus(), e.getObservacao(), e.getQtdMinVoluntarios(), 
						 e.getQtdMaxVoluntarios(), e.getVlrPrevisto(), e.getVlrArrecadado(), e.getVlrExecutado(), e.getVoluntarios()));
			}
		
		return res;
	}
	
}
