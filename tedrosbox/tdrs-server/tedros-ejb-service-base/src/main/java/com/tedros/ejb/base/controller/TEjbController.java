package com.tedros.ejb.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.ejb.base.service.ITEjbService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbController<E extends ITEntity> implements ITEjbController<E> {

	
	public abstract ITEjbService<E> getService();
	
	
	public TResult<E> findById(E entidade) {
		try{
			entidade = getService().findById(entidade);
			return new TResult<E>(TState.SUCCESS, entidade);
		}catch(Exception e){
			return processException(entidade, e);
		}
	}
	
	public TResult<E> find(E entidade) {
		try{
			entidade = getService().find(entidade);
			return new TResult<E>(TState.SUCCESS, entidade);
		}catch(Exception e){
			return processException(entidade, e);
		}
	}
	
	public TResult<List<E>> findAll(E entity){
		try{
			List<E> list = getService().findAll(entity);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(entity, e);
		}
	}

	@Override
	public TResult<E> save(E entidade) {
		try{
			E e = getService().save(entidade);
			return new TResult<E>(TState.SUCCESS, e);
		}catch(Exception e){
			return processException(entidade, e);
		}
	}

	@Override
	public TResult<E> remove(E entidade) {
		try{
			getService().remove(entidade);
			return new TResult<E>(TState.SUCCESS);
			
		}catch(Exception e){
			return processException(entidade, e);
		}
	}

	@Override
	public TResult<List<E>> listAll(Class<? extends ITEntity> entidade) {
		
		try{
			List<E> list = getService().listAll(entidade);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(null, e);
		}
	}

	@Override
	public TResult<Map<String, Object>> pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc) {
		try{
			Long count  = getService().countAll(entidade.getClass());
			
			List<E> list = getService().pageAll(entidade, firstResult, maxResult, orderByAsc);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(entidade, e);
		}
	}

	@Override
	public TResult<Map<String, Object>> findAll(E entidade, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) {
		try{
			Number count  =  getService().countFindAll(entidade, containsAnyKeyWords);
			
			List<E> list = getService().findAll(entidade, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count.longValue());
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(entidade, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T processException(E entidade, Exception e) {
		e.printStackTrace();
		if(e instanceof OptimisticLockException || e.getCause() instanceof OptimisticLockException){
			TResult<E> result = find(entidade);
			
			String message = (result.getValue()==null) ? "REMOVED" : "OUTDATED";
			
			return (T) new TResult<>(TState.OUTDATED, message, result.getValue());
		}else if(e instanceof EJBTransactionRolledbackException) {
			return (T) new TResult<>(TState.ERROR,true, e.getCause().getMessage());
		}else if(e instanceof EJBException) {
			return (T) new TResult<>(TState.ERROR,true, e.getCause().getMessage());
		}else{
			return (T) new TResult<>(TState.ERROR, e.getMessage());
		}
	}
	
	

}
