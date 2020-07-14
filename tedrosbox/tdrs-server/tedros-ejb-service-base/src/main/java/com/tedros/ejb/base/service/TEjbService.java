package com.tedros.ejb.base.service;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.entity.ITEntity;

@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public abstract class TEjbService<E extends ITEntity> implements ITEjbService<E> {

	
	public abstract ITGenericBO<E> getBussinesObject();
	
	
	@Override
	
	public E find(E entidade) throws Exception {
		return entidade = internalFind(entidade);
	}
	
	public List<E> findAll(E entity)throws Exception{
		return getBussinesObject().findAll(entity);
	}

	private E internalFind(E entidade) throws Exception {
		entidade = getBussinesObject().find(entidade);
		return entidade;
	}

	@Override
	public E save(E entidade) throws Exception {
		return getBussinesObject().save(entidade);
	}

	@Override
	public void remove(E entidade) throws Exception {
		entidade = internalFind(entidade);
		getBussinesObject().remove(entidade);
	}

	@Override
	public List<E> listAll(Class<? extends ITEntity> entidade) throws Exception {
		return getBussinesObject().listAll(entidade);
	}
	
	@Override
	public List<E> pageAll(Class<? extends ITEntity> entidade, int firstResult, int maxResult) throws Exception {
		return getBussinesObject().pageAll(entidade, firstResult, maxResult);
	}

	@Override
	public Long countAll(Class<? extends ITEntity> entidade) throws Exception {
		return getBussinesObject().countAll(entidade);
	}
	
	
	
	

}
