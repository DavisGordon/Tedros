package com.tedros.ejb.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbController<E extends ITEntity> implements ITEjbController<E> {

	
	public abstract ITEjbService<E> getService();
	
	
	public TResult<E> findById(E entidade) {
		try{
			entidade = getService().findById(entidade);
			return new TResult<E>(EnumResult.SUCESS, entidade);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<E>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<E> find(E entidade) {
		try{
			entidade = getService().find(entidade);
			return new TResult<E>(EnumResult.SUCESS, entidade);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<E>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<List<E>> findAll(E entity){
		try{
			List<E> list = getService().findAll(entity);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<E> save(E entidade) {
		try{
			E e = getService().save(entidade);
			return new TResult<E>(EnumResult.SUCESS, e);
		}catch(Exception e){
			return processException(entidade, e);
		}
	}

	@Override
	public TResult<E> remove(E entidade) {
		try{
			getService().remove(entidade);
			return new TResult<E>(EnumResult.SUCESS);
			
		}catch(Exception e){
			return processException(entidade, e);
		}
	}

	@Override
	public TResult<List<E>> listAll(Class<? extends ITEntity> entidade) {
		
		try{
			List<E> list = getService().listAll(entidade);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<Map<String, Object>> pageAll(Class<? extends ITEntity> entidade, int firstResult, int maxResult) {
		try{
			Long count  = getService().countAll(entidade);
			
			List<E> list = getService().pageAll(entidade, firstResult, maxResult);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(EnumResult.SUCESS, map);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<Map<String, Object>> findAll(E entidade, int firstResult, int maxResult) {
		try{
			Long count  = (long) getService().countFindAll(entidade);
			
			List<E> list = getService().findAll(entidade, firstResult, maxResult);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(EnumResult.SUCESS, map);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	public TResult<E> processException(E entidade, Exception e) {
		e.printStackTrace();
		if(e instanceof OptimisticLockException){
			TResult<E> result = find(entidade);
			
			String message = (result.getValue()==null) ? "REMOVED" : "OUTDATED";
			
			return new TResult<E>(EnumResult.OUTDATED, message, result.getValue());
		}else{
			return new TResult<E>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	

}
