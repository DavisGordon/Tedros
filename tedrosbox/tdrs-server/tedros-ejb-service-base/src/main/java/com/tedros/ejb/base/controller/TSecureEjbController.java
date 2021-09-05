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
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.service.ITEjbService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TSecureEjbController<E extends ITEntity> implements ITSecureEjbController<E> {

	
	protected abstract ITEjbService<E> getService();
	
	
	public TResult<E> findById(TAccessToken token, E entidade) {
		try{
			entidade = getService().findById(entidade);
			return new TResult<E>(EnumResult.SUCESS, entidade);
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}
	
	public TResult<E> find(TAccessToken token, E entidade) {
		try{
			entidade = getService().find(entidade);
			return new TResult<E>(EnumResult.SUCESS, entidade);
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}
	
	public TResult<List<E>> findAll(TAccessToken token, E entity){
		try{
			List<E> list = getService().findAll(entity);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}

	@Override
	public TResult<E> save(TAccessToken token, E entidade) {
		try{
			E e = getService().save(entidade);
			return new TResult<E>(EnumResult.SUCESS, e);
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}

	@Override
	public TResult<E> remove(TAccessToken token, E entidade) {
		try{
			getService().remove(entidade);
			return new TResult<E>(EnumResult.SUCESS);
			
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}

	@Override
	public TResult<List<E>> listAll(TAccessToken token, Class<? extends ITEntity> entidade) {
		
		try{
			List<E> list = getService().listAll(entidade);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			return processException(token, null, e);
		}
	}

	@Override
	public TResult<Map<String, Object>> pageAll(TAccessToken token, E entidade, int firstResult, int maxResult, boolean orderByAsc) {
		try{
			Long count  = getService().countAll(entidade.getClass());
			
			List<E> list = getService().pageAll(entidade, firstResult, maxResult, orderByAsc);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(EnumResult.SUCESS, map);
			
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}

	@Override
	public TResult<Map<String, Object>> findAll(TAccessToken token, E entidade, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) {
		try{
			Number count  =  getService().countFindAll(entidade, containsAnyKeyWords);
			
			List<E> list = getService().findAll(entidade, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count.longValue());
			map.put("list", list);
			
			return new TResult<>(EnumResult.SUCESS, map);
			
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T processException(TAccessToken token, E entidade, Exception e) {
		e.printStackTrace();
		if(e instanceof OptimisticLockException || e.getCause() instanceof OptimisticLockException){
			TResult<E> result = find(token, entidade);
			
			String message = (result.getValue()==null) ? "REMOVED" : "OUTDATED";
			
			return (T) new TResult<>(EnumResult.OUTDATED, message, result.getValue());
		}else if(e instanceof EJBTransactionRolledbackException) {
			return (T) new TResult<>(EnumResult.ERROR,true, e.getCause().getMessage());
		}else if(e instanceof EJBException) {
			return (T) new TResult<>(EnumResult.ERROR,true, e.getCause().getMessage());
		}else{
			return (T) new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	

}
