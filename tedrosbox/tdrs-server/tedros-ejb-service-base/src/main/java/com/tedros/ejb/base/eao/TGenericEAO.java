package com.tedros.ejb.base.eao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.QueryByExamplePolicy;
import org.eclipse.persistence.queries.ReadAllQuery;

import com.tedros.ejb.base.entity.ITEntity;

public class TGenericEAO<E extends ITEntity> implements ITGenericEAO<E>  {
	
	
	public E find(EntityManager em, E entity)throws Exception{
		
		List<E> results = findAll(em, entity);
		
		return (results!=null && results.size()>0) ? results.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findAll(EntityManager em, E entity)throws Exception{
		
		ReadAllQuery query = new ReadAllQuery(entity.getClass());
		query.setExampleObject(entity);
		// Query by example policy section adds like and greaterThan 
		QueryByExamplePolicy policy = new QueryByExamplePolicy();
		policy.addSpecialOperation(String.class, "like");
		query.setQueryByExamplePolicy(policy);
		
		return (List<E>) ((JpaEntityManager) em.getDelegate()).getActiveSession().executeQuery(query);
	}
	
	
	
	public void beforePersist(EntityManager em, E entity)throws Exception{
		
	}
	
	public void afterPersist(EntityManager em, E entity)throws Exception{
		
	}
	
	public void beforeRemove(EntityManager em, E entity)throws Exception{
		
	}
	
	public void afterRemove(EntityManager em, E entity)throws Exception{
		
	}
	
	public void beforeMerge(EntityManager em, E entity)throws Exception{
		
	}
	
	public void afterMerge(EntityManager em, E entity)throws Exception{
		
	}
	
	
	/**
	 * Persiste uma entity
	 * */
	public void persist(EntityManager em, E entity)throws Exception{
		beforePersist(em, entity);
		if(entity.isNew())
			entity.setInsertDate(new Date());
		else
			entity.setLastUpdate(new Date());
		em.persist(entity);
		afterPersist(em, entity);
	}
	
	public E merge(EntityManager em, E entity)throws Exception{
		beforeMerge(em, entity);
		if(entity.isNew())
			entity.setInsertDate(new Date());
		else
			entity.setLastUpdate(new Date());
		E e = em.merge(entity);
		afterMerge(em, e);
		return e;
	}
	
	
	/**
	 * Remove uma entity
	 * */
	public void remove(EntityManager em, E entity)throws Exception{
		beforeRemove(em, entity);
		em.remove(entity);
		afterRemove(em, entity);
	}
	/**
	 * Retorna uma lista com todas as entitys persistidas
	 * */
	@SuppressWarnings("unchecked")
	public List<E> listAll(EntityManager em, Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		return em.createQuery(cq).getResultList();
	}
	/**
	 * Retorna uma lista paginada
	 * */
	@SuppressWarnings("unchecked")
	public List<E> pageAll(EntityManager em, Class<? extends ITEntity> entity, int firstResult, int maxResult)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) cb.createQuery(entity);
		Root<E> root = (Root<E>) cq.from(entity);
		cq.select(root);
		TypedQuery<E> qry = em.createQuery(cq);
		qry.setFirstResult(firstResult);
		qry.setMaxResults(maxResult);
		return qry.getResultList();
	}
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(EntityManager em, Class<? extends ITEntity> entity)throws Exception{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = (CriteriaQuery<Long>) cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(entity)));
		return (Long) em.createQuery(cq).getSingleResult();
	}
}
