package com.tedros.ejb.base.controller;

import java.util.List;

import javax.persistence.OptimisticLockException;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

public abstract class TEjbController<E extends ITEntity> implements ITEjbController<E> {

	
	public abstract ITEjbService<E> getService();
	
	
	@Override
	
	public TResult<E> find(E entidade) {
		try{
			entidade = internalFind(entidade);
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

	private E internalFind(E entidade) throws Exception {
		entidade = getService().find(entidade);
		return entidade;
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
			entidade = internalFind(entidade);
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
	public TResult<List<E>> pageAll(Class<? extends ITEntity> entidade, int firstResult, int maxResult) {
		try{
			List<E> list = getService().pageAll(entidade, firstResult, maxResult);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<Long> countAll(Class<? extends ITEntity> entidade) {
		try{
			Long count  = getService().countAll(entidade);
			return new TResult<Long>(EnumResult.SUCESS, count);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<Long>(EnumResult.ERROR, e.getMessage());
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
