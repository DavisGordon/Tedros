package org.tedros.server.ejb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;

import org.tedros.server.controller.ITEjbController;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.service.ITEjbService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbController<E extends ITEntity> implements ITEjbController<E> {

	
	public abstract ITEjbService<E> getService();
	
	protected void processEntity(E entity) {
		
	}
	
	protected void processEntityList(List<E> entities) {
		
	}
	
	
	public TResult<E> findById(E entity) {
		try{
			entity = getService().findById(entity);
			processEntity(entity);
			return new TResult<E>(TState.SUCCESS, entity);
		}catch(Exception e){
			return processException(entity, e);
		}
	}
	
	public TResult<E> find(E entity) {
		try{
			entity = getService().find(entity);
			processEntity(entity);
			return new TResult<E>(TState.SUCCESS, entity);
		}catch(Exception e){
			return processException(entity, e);
		}
	}
	
	public TResult<List<E>> findAll(E entity){
		try{
			List<E> list = getService().findAll(entity);
			processEntityList(list);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(entity, e);
		}
	}

	@Override
	public TResult<E> save(E entity) {
		try{
			E e = getService().save(entity);
			processEntity(e);
			return new TResult<E>(TState.SUCCESS, e);
		}catch(Exception e){
			return processException(entity, e);
		}
	}

	@Override
	public TResult<E> remove(E entity) {
		try{
			getService().remove(entity);
			return new TResult<E>(TState.SUCCESS);
			
		}catch(Exception e){
			return processException(entity, e);
		}
	}

	@Override
	public TResult<List<E>> listAll(Class<? extends ITEntity> entity) {
		
		try{
			List<E> list = getService().listAll(entity);
			processEntityList(list);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(null, e);
		}
	}

	@Override
	public TResult<Map<String, Object>> pageAll(E entity, int firstResult, int maxResult, boolean orderByAsc) {
		try{
			Long count  = getService().countAll(entity.getClass());
			
			List<E> list = getService().pageAll(entity, firstResult, maxResult, orderByAsc);
			processEntityList(list);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(entity, e);
		}
	}

	@Override
	public TResult<Map<String, Object>> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) {
		try{
			Number count  =  getService().countFindAll(entity, containsAnyKeyWords);
			
			List<E> list = getService().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
			processEntityList(list);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count.longValue());
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(entity, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T processException(E entity, Exception e) {
		e.printStackTrace();
		if(e instanceof OptimisticLockException || e.getCause() instanceof OptimisticLockException){
			TResult<E> result = find(entity);
			
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
