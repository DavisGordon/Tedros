package com.tedros.ejb.base.eao;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.QueryByExamplePolicy;
import org.eclipse.persistence.queries.ReadAllQuery;

import com.tedros.ejb.base.entity.ITEntity;

public class TGenericEAO<E extends ITEntity> implements ITGenericEAO<E>  {
	
	@PersistenceContext(unitName = "tedros_core_pu", type=PersistenceContextType.TRANSACTION)
    private EntityManager em;
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	@PostConstruct
	protected void init(){
		getEntityManager().setProperty("javax.persistence.cache.retrieveMode", "BYPASS");
		//getEntityManager().setProperty("javax.persistence.cache.storeMode", "BYPASS");
	}
	
	@SuppressWarnings("unchecked")
	public E findById(E entity)throws Exception{
		return (E) em.find(entity.getClass(), entity.getId());
	}
	
	@SuppressWarnings("unchecked")
	public E find(E entity)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		List<E> results = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		return (results!=null && results.size()>0) ? results.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findAll(E entity)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, "like");
		query.setQueryByExamplePolicy(policy);
		
		return (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
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
	/**
	 * Retorna uma lista com todas as entitys persistidas
	 * */
	@SuppressWarnings("unchecked")
	public List<E> listAll(Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		return em.createQuery(cq).getResultList();
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
		
		return em.createQuery(cq).getResultList();
	}
	/**
	 * Retorna uma lista paginada
	 * */
	@SuppressWarnings("unchecked")
	public List<E> pageAll(Class<? extends ITEntity> entity, int firstResult, int maxResult)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		TypedQuery<E> qry = em.createQuery(cq);
		qry.setFirstResult(firstResult);
		qry.setMaxResults(maxResult);
		return qry.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findAll(E entity, int firstResult, int maxResult)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, "like");
		query.setQueryByExamplePolicy(policy);
		query.setFirstResult(firstResult);
		query.setMaxRows(maxResult);
		
		return (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	public int countFindAll(E entity)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, "like");
		query.setQueryByExamplePolicy(policy);
		
		List<E> lst = (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
		
		return lst.size();
	}
	
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = (CriteriaQuery<Long>) cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(entity)));
		return (Long) em.createQuery(cq).getSingleResult();
	}
}
