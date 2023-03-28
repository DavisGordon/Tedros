package org.tedros.server.cdi.eao;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;

public abstract class TGenericEAO<E extends ITEntity> implements ITGenericEAO<E>  {
	
	@PersistenceContext(unitName = "tedros_core_pu", type=PersistenceContextType.TRANSACTION)
    private EntityManager em;
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	@SuppressWarnings("unchecked")
	public E findById(E entity)throws Exception{
		E e = (E) em.find(entity.getClass(), entity.getId());
		afterFind(e);
		return e;
	}

	public E find(E entity)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		List<E> results = executeAndGetList(query);
		E e = (results!=null && results.size()>0) ? results.get(0) : null;
		afterFind(e);
		return e;
	}
	
	public List<E> findAll(E entity)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, "like");
		query.setQueryByExamplePolicy(policy);
		
		return executeAndGetList(query);
	}
	
	
	public void beforePersist(E entity)throws Exception{
		
	}
	
	public void afterPersist(E entity)throws Exception{
		
	}
	
	public void beforeRemove(E entity)throws Exception{
		
	}
	
	public void afterRemove(E entity)throws Exception{
		
	}
	
	public void beforeMerge(E entity)throws Exception{
		
	}
	
	public void afterMerge(E entity)throws Exception{
		
	}
	
	public void afterFind(E entity)throws Exception{
		
	}
	
	public void afterListAll(List<E> lst)throws Exception{
		
	}
	
	public void afterFindAll(List<E> lst)throws Exception{
		
	}
	
	public void afterPageAll(List<E> lst)throws Exception{
		
	}
	
	/**
	 * Persiste uma entity
	 * */
	public void persist(E entity)throws Exception{
		beforePersist(entity);
		if(entity.isNew())
			entity.setInsertDate(new Date());
		else
			entity.setLastUpdate(new Date());
		em.persist(entity);
		afterPersist(entity);
	}
	
	public E merge(E entity)throws Exception{
		beforeMerge(entity);
		if(entity.isNew())
			entity.setInsertDate(new Date());
		else
			entity.setLastUpdate(new Date());
		E e = em.merge(entity);
		afterMerge(e);
		return e;
	}
	
	/**
	 * Remove uma entity
	 * */
	@SuppressWarnings("unchecked")
	public void remove(E entity)throws Exception{
		beforeRemove(entity);
		if(!em.contains(entity)){
			entity = (E) em.find(entity.getClass(), entity.getId());
		}
		if(entity!=null){
			em.remove(entity);
			afterRemove(entity);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<E> select(TSelect<E> sel){
		
		StringBuilder sb = new StringBuilder("select e from ");
		sb.append(sel.getType().getSimpleName()+ " e ");
		
		sel.getConditions().forEach(b->{
			if(b.getOperator()!=null)
				sb.append(b.getOperator().name()).append(" ");
			if(b.getCondition().getOperator().equals(TCompareOp.LIKE))
				sb.append("lower(");
			sb.append(b.getCondition().getField());
			if(b.getCondition().getOperator().equals(TCompareOp.LIKE))
				sb.append(")");
			sb.append(" ").append(b.getCondition().getOperator().getValue()).append(" ");
			if(b.getCondition().getOperator().getValue().equals(TCompareOp.LIKE.getValue()))
				sb.append("'");
			sb.append(":").append(b.getCondition().getField()).append("_");
			if(b.getCondition().getOperator().getValue().equals(TCompareOp.LIKE.getValue()))
				sb.append("'");
			sb.append(" ");
		});
		
		if(sel.getOrdenations()!=null) {
			StringBuilder sb1 = new StringBuilder("");
			sel.getOrdenations().forEach(f->{
				if("".equals(sb1.toString()))
					sb1.append("order by ");
				else
					sb1.append(", ");
				sb1.append(f.getField());
			});
			sb.append(sb1);
		}
			
		Query qry = this.getEntityManager().createQuery(sb.toString());
		
		sel.getConditions().forEach(b->{
			qry.setParameter(b.getCondition().getField()+"_", b.getCondition().getValue());
		});
		
		return qry.getResultList();
		
	}
	
	/**
	 * Retorna uma lista com todas as entitys persistidas
	 * */
	@SuppressWarnings("unchecked")
	public List<E> listAll(Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		//List<E> lst = em.createQuery(cq).getResultList();
		List<E> lst = executeAndGetList(cq);
		afterListAll(lst);
		return lst;
	}
	/**
	 * Retorna uma lista com todas as entitys persistidas
	 * */
	@SuppressWarnings("unchecked")
	public List<E> listAll(Class<? extends ITEntity> entity, boolean asc )throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		if(asc)
			cq.orderBy(cb.asc(root.get("id")));
		else
			cq.orderBy(cb.desc(root.get("id")));
		
		List<E> lst = this.executeAndGetList(cq); //em.createQuery(cq).getResultList();
		afterListAll(lst);
		return lst;
	}
	/**
	 * Retorna uma lista paginada
	 * */
	@SuppressWarnings("unchecked")
	public List<E> pageAll(E entity, int firstResult, int maxResult, boolean orderByAsc)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity.getClass());
		Root<E> root = (Root<E>) cq.from(entity.getClass());
		cq.select(root);
		
		if(entity.getOrderBy()!=null && !entity.getOrderBy().isEmpty()) {
			for(String f : entity.getOrderBy())
				if(orderByAsc)
					cq.orderBy(cb.asc(root.get(f)));
				else
					cq.orderBy(cb.desc(root.get(f)));
		}
		
		TypedQuery<E> qry = em.createQuery(cq);
		qry.setFirstResult(firstResult);
		qry.setMaxResults(maxResult);
		
		List<E> lst = qry.getResultList();
		afterPageAll(lst);
		return lst;
	}


	public List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
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
		
		List<E> lst = (List<E>) this.executeAndGetList(query); //((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		afterFindAll(lst);
		return lst;
	}
	
	@SuppressWarnings("rawtypes")
	public Integer countFindAll(E entity, boolean containsAnyKeyWords)throws Exception{
		ExpressionBuilder eb = new ExpressionBuilder();
		ReportQuery query = new ReportQuery(entity.getClass(), eb);
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, (containsAnyKeyWords) ? "containsAnyKeyWords" : "like");
		query.setQueryByExamplePolicy(policy);
		
		query.addCount();
		
		//List<E> lst = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		//ReportQueryResult res = (ReportQueryResult) ((Vector)((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query)).get(0);
		ReportQueryResult res = (ReportQueryResult) ((Vector)((JpaEntityManager) em.getDelegate()).createQuery(query).getResultList()).get(0);
		Integer total = (Integer) res.get("COUNT");
		return  total;
	}
	
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = (CriteriaQuery<Long>) cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(entity)));
		return (Long) this.executeAndGet(cq) ;//em.createQuery(cq).getSingleResult();
	}
	
	/**
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> executeAndGetList(ReadAllQuery query) {
		List<E> results = (List<E>) ((JpaEntityManager)em.getDelegate()).createQuery(query).getResultList();
		//List<E> results = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		return results;
	}
	
	/**
	 * @param cq
	 * @return
	 */
	protected List<E> executeAndGetList(CriteriaQuery<E> cq) {
		List<E> results = (List<E>) ((JpaEntityManager)em.getDelegate()).createQuery(cq).getResultList();
		//List<E> results = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		return results;
	}
	
	/**
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T executeAndGet(ReadAllQuery query) {
		T results = (T) ((JpaEntityManager)em.getDelegate()).createQuery(query).getSingleResult();
		//List<E> results = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		return results;
	}
	
	/**
	 * @param cq
	 * @return
	 */
	protected <T> T executeAndGet(CriteriaQuery<T> cq) {
		T results = (T) ((JpaEntityManager)em.getDelegate()).createQuery(cq).getSingleResult();
		//List<E> results = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		return results;
	}
	
}
