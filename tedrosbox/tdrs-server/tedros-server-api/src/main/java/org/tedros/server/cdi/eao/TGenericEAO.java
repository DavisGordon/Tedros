package org.tedros.server.cdi.eao;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
	
	@Override
	@SuppressWarnings("unchecked")
	public E findById(E entity)throws Exception{
		E e = (E) em.find(entity.getClass(), entity.getId());
		afterFind(e);
		return e;
	}
	
	@Override
	public E find(E entity)throws Exception{
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		List<E> results = executeAndGetList(query);
		E e = (results!=null && !results.isEmpty()) 
				? results.get(0) 
						: null;
		afterFind(e);
		return e;
	}
	
	@Override
	public List<E> findAll(E entity)throws Exception{
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, "like");
		query.setQueryByExamplePolicy(policy);
		
		return executeAndGetList(query);
	}
	
	/**
	 * Persiste uma entity
	 * */
	@Override
	public void persist(E entity)throws Exception{
		beforePersist(entity);
		if(entity.isNew())
			entity.setInsertDate(new Date());
		else
			entity.setLastUpdate(new Date());
		em.persist(entity);
		afterPersist(entity);
	}
	
	@Override
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
	@Override
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
	
	@Override
	@SuppressWarnings("unchecked")
	public List<E> search(TSelect<E> sel){
		Query qry = createSearchQuery(sel, false);
		return qry.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<E> search(TSelect<E> sel, int firstResult, int maxResult){
		
		Query qry = createSearchQuery(sel, false);
		qry.setFirstResult(firstResult);
		qry.setMaxResults(maxResult);
		return qry.getResultList();
		
	}
	
	@Override
	public Long countSearch(TSelect<E> sel){
		Query qry = createSearchQuery(sel, true);
		return (Long) qry.getSingleResult();
		
	}	
	
	/**
	 * Retorna uma lista com todas as entitys persistidas
	 * */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> listAll(Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);		
		List<E> lst = executeAndGetList(cq);
		afterListAll(lst);
		return lst;
	}
	
	/**
	 * Retorna uma lista com todas as entitys persistidas do usuario
	 * */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> listAll(Long userId, Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		cq.where(cb.equal(root.get(CREATED_BY_USER_ID), userId));
		
		List<E> lst = executeAndGetList(cq);
		afterListAll(lst);
		return lst;
	}
	
	/**
	 * Retorna uma lista com todas as entitys persistidas
	 * */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> listAll(Class<? extends ITEntity> entity, boolean asc )throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		if(asc)
			cq.orderBy(cb.asc(root.get(ID)));
		else
			cq.orderBy(cb.desc(root.get(ID)));
		
		List<E> lst = this.executeAndGetList(cq); 
		afterListAll(lst);
		return lst;
	}
	
	/**
	 * Retorna uma lista com todas as entitys persistidas
	 * */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> listAll(Long userId, Class<? extends ITEntity> entity, boolean asc )throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		cq.where(cb.equal(root.get(CREATED_BY_USER_ID), userId));
		
		if(asc)
			cq.orderBy(cb.asc(root.get(ID)));
		else
			cq.orderBy(cb.desc(root.get(ID)));
		
		List<E> lst = this.executeAndGetList(cq); 
		afterListAll(lst);
		return lst;
	}	
	
	/**
	 * Retorna uma lista paginada
	 * */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> pageAll(E entity, int firstResult, int maxResult, boolean orderByAsc)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity.getClass());
		Root<E> root = (Root<E>) cq.from(entity.getClass());
		root.alias("e");
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

	@Override
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
		
		List<E> lst = this.executeAndGetList(query);
		afterFindAll(lst);
		return lst;
	}
	
	@Override
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
		
		ReportQueryResult res = (ReportQueryResult) 
				((Vector)((JpaEntityManager) em.getDelegate()).createQuery(query).getResultList()).get(0);
		
		return  (Integer) res.get("COUNT");
	}
		
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	@Override
	public Long countAll(Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(entity)));
		return this.executeAndGet(cq) ;
	}
		
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	@Override
	public Long countAll(Long userId, Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Long> cq = cb.createQuery(Long.class);	    
	    Root<?> root = cq.from(entity);  
	    cq.select(cb.count(root));
	    cq.where(cb.equal(root.get(CREATED_BY_USER_ID), userId));
		return this.executeAndGet(cq);
	}
	
	/**
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> executeAndGetList(ReadAllQuery query) {
		return ((JpaEntityManager)em.getDelegate()).createQuery(query).getResultList();
	}
	
	/**
	 * @param cq
	 * @return
	 */
	protected List<E> executeAndGetList(CriteriaQuery<E> cq) {
		return ((JpaEntityManager)em.getDelegate()).createQuery(cq).getResultList();
	}
	
	/**
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T executeAndGet(ReadAllQuery query) {
		return (T) ((JpaEntityManager)em.getDelegate()).createQuery(query).getSingleResult();
	}
	
	/**
	 * @param cq
	 * @return
	 */
	protected <T> T executeAndGet(CriteriaQuery<T> cq) {
		return ((JpaEntityManager)em.getDelegate()).createQuery(cq).getSingleResult();
	}
	
	private Query createSearchQuery(TSelect<E> sel, boolean count) {
		StringBuilder sb = new StringBuilder("select ");
		if(count)
			sb.append("count(");
		else
			sb.append("distinct ");
		sb.append(sel.getAlias());
		if(count)
			sb.append(") as total");
		sb.append(" ");
		sb.append("from ");
		sb.append(sel.getType().getSimpleName()).append(" ");
		sb.append(sel.getAlias()).append(" ");
		
		if(sel.getJoins()!=null)
			sel.getJoins().forEach(j->{
				sb.append(j.getType().getValue()).append(" ");
				sb.append(j.getAlias()).append(".");
				sb.append(j.getField()).append(" ");
				sb.append(j.getJoinAlias()).append(" ");
			});
		
		if(sel.getConditions()!=null && !sel.getConditions().isEmpty()) {
			sb.append("where ");
			
			sel.getConditions().forEach(b->{
				if(b.getCondition().getValue()!=null) {
					if(b.getOperator()!=null)
						sb.append(b.getOperator().name().toLowerCase()).append(" ");
					
					if(b.getCondition().getOperator().equals(TCompareOp.LIKE))
						sb.append("lower(");
					
					sb.append(b.getCondition().getAlias())
					.append(".").append(b.getCondition().getField());
					
					if(b.getCondition().getOperator().equals(TCompareOp.LIKE))
						sb.append(")");
					
					String qryParam = b.getCondition().getField() +"_"+ UUID.randomUUID().toString().substring(2, 8);
					b.getCondition().setQryParam(qryParam);
					
					sb.append(" ").append(b.getCondition().getOperator().getValue()).append(" ");
					sb.append(":").append(qryParam);
					sb.append(" ");
				}
			});
		}
		if(!count && sel.getOrdenations()!=null) {
			StringBuilder sb1 = new StringBuilder("");
			sel.getOrdenations().forEach(f->{
				if("".equals(sb1.toString()))
					sb1.append("order by ");
				else
					sb1.append(", ");
				if(f.getAlias()!=null)
					sb1.append(f.getAlias()).append(".");
				sb1.append(f.getField());
			});
			if(sel.isAsc())
				sb1.append(" ").append("asc");
			else
				sb1.append(" ").append("desc");
			sb.append(sb1);
				
		}
		
		Query qry = this.getEntityManager().createQuery(sb.toString());
		if(sel.getConditions()!=null && !sel.getConditions().isEmpty()) {
			sel.getConditions().forEach(b->{
				if(b.getCondition().getValue()!=null)
					qry.setParameter(
						b.getCondition().getQryParam(), 
						b.getCondition().getOperator().equals(TCompareOp.LIKE) 
							? "%"+b.getCondition().getValue().toString().toLowerCase()+"%"
									: b.getCondition().getValue()
					);
			});
		}
		return qry;
	}
	
}
