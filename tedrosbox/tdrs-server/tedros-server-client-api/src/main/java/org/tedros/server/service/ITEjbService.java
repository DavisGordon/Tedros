package org.tedros.server.service;

import java.util.List;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

public interface ITEjbService<E extends ITEntity> {

	/**
	 * Search for entities
	 * */
	List<E> search(TSelect<E> sel);

	/**
	 * Search for entities
	 * */
	List<E> search(TSelect<E> sel, int firstResult, int maxResult);
	
	/**
	 * Count a searched entities
	 * */
	Long countSearch(TSelect<E> sel);
	
	/**
	 * Find an entity by id
	 * */
	E findById(E entidade)throws Exception;
	
	/**
	 * Returns the first entity searched with attributes filled in
	 * */
	E find(E entidade)throws Exception ;
	
	/**
	 * Search for entities with attributes filled in
	 * */
	List<E> findAll(E entity)throws Exception;
	
	/**
	 * Save an entity
	 * */
	E save(E entidade)throws Exception;
	/**
	 * Remove/delete an entity
	 * */
	void remove(E entidade)throws Exception;
	
	/**
	 * List all typed entities
	 * */
	List<E> listAll(Class<? extends ITEntity> entidadeClass)throws Exception;
	
	/**
	 * Paginate all typed entity
	 * */
	List<E> pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc)throws Exception;
	
	/**
	 * Find and page the result
	 * */
	List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception;

	/**
	 * Return the total entities found in find all
	 * */
	Integer countFindAll(E entity, boolean containsAnyKeyWords)throws Exception;
	
	/**
	 * Return the total entities
	 * */
	Long countAll(Class<? extends ITEntity> entidade)throws Exception;
	
}
