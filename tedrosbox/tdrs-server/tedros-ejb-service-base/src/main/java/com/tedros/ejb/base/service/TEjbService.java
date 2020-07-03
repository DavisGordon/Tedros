package com.tedros.ejb.base.service;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.TResult.EnumResult;

@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public abstract class TEjbService<E extends ITEntity> implements ITEjbService<E> {

	
	public abstract ITGenericBO<E> getBussinesObject();
	
	
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
	
	public TResult<List<E>> findAll(E entity)throws Exception{
		try{
			List<E> list = getBussinesObject().findAll(entity);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}

	private E internalFind(E entidade) throws Exception {
		entidade = getBussinesObject().find(entidade);
		return entidade;
	}

	@Override
	public TResult<E> save(E entidade) {
		try{
			E e = getBussinesObject().save(entidade);
			return new TResult<E>(EnumResult.SUCESS, e);
		}catch(Exception e){
			return processException(entidade, e);
		}
	}

	@Override
	public TResult<E> remove(E entidade) {
		try{
			entidade = internalFind(entidade);
			getBussinesObject().remove(entidade);
			return new TResult<E>(EnumResult.SUCESS);
			
		}catch(Exception e){
			return processException(entidade, e);
		}
	}

	@Override
	public TResult<List<E>> listAll(Class<? extends ITEntity> entidade) {
		
		try{
			List<E> list = getBussinesObject().listAll(entidade);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<List<E>> pageAll(Class<? extends ITEntity> entidade, int firstResult, int maxResult) {
		try{
			List<E> list = getBussinesObject().pageAll(entidade, firstResult, maxResult);
			return new TResult<List<E>>(EnumResult.SUCESS, list);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	public TResult<Long> countAll(Class<? extends ITEntity> entidade) {
		try{
			Long count  = getBussinesObject().countAll(entidade);
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
			
			return new TResult<E>(EnumResult.OUTDATED, message, e.getMessage(), result.getValue());
		}else{
			return new TResult<E>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	

}
