package org.tedros.server.controller;

import java.util.List;
import java.util.Map;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;

public interface ITEjbController<E extends ITEntity> extends ITBaseController {
	/**
	 * Search for entities
	 * */
	TResult<List<E>> search(TSelect<E> sel);
	
	/**
	 * Search for entities
	 * */
	TResult<Map<String, Object>> search(TSelect<E> sel, int firstResult, int maxResult);
		
	/**
	 * Find an entity by id
	 * */
	TResult<E> findById(E entidade)throws Exception;
	
	/**
	 * Returns the first entity searched with attributes filled in
	 * */
	TResult<E> find(E entidade);
	
	/**
	 * Search by filled attributes
	 * */
	TResult<List<E>> findAll(E entity);
	
	/**
	 * Save an entity
	 * */
	TResult<E> save(E entidade);
	
	/**
	 * Remove an entity
	 * */
	TResult<E> remove(E entidade);
	
	/**
	 * Returns a list of all persisted entities
	 * */
	TResult<List<E>> listAll(Class<? extends ITEntity> entidadeClass);
	
	/**
	 * Returns a paginated list
	 * */
	TResult<Map<String, Object>> pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc);
	
	/**
	 * Returns a paginated search
	 * */
	TResult<Map<String, Object>> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception;

}
