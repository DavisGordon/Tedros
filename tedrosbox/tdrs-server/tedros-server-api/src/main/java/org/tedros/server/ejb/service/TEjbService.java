package org.tedros.server.ejb.service;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;
import org.tedros.server.service.ITEjbService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbService<E extends ITEntity> implements ITEjbService<E> {

	
	public abstract ITGenericBO<E> getBussinesObject();
	
	/**
	 * Search for entities
	 * */
	public List<E> search(TSelect<E> sel){
		return getBussinesObject().search(sel);
	}
	
	public E findById(E entidade)throws Exception{
		return getBussinesObject().findById(entidade);
	}
	
	public E find(E entidade) throws Exception {
		return getBussinesObject().find(entidade);
	}
	
	public List<E> findAll(E entity)throws Exception{
		return getBussinesObject().findAll(entity);
	}

	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public E save(E entidade) throws Exception {
		return getBussinesObject().save(entidade);
	}

	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void remove(E entidade) throws Exception {
		getBussinesObject().remove(entidade);
	}

	@Override
	public List<E> listAll(Class<? extends ITEntity> entidade) throws Exception {
		return getBussinesObject().listAll(entidade);
	}
	
	@Override
	public List<E> pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc) throws Exception {
		return getBussinesObject().pageAll(entidade, firstResult, maxResult, orderByAsc);
	}

	@Override
	public Long countAll(Class<? extends ITEntity> entidade) throws Exception {
		return getBussinesObject().countAll(entidade);
	}

	@Override
	public List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) throws Exception {
		return getBussinesObject().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
	}

	@Override
	public Integer countFindAll(E entity, boolean containsAnyKeyWords) throws Exception {
		return getBussinesObject().countFindAll(entity, containsAnyKeyWords);
	}
	
	
	
	

}
