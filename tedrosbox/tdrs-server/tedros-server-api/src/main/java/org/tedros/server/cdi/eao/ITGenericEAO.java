package org.tedros.server.cdi.eao;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

public interface ITGenericEAO<E extends ITEntity> {
	
	/**
	 * @return {@link EntityManager}
	 */
	EntityManager getEntityManager();

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
	 * Return the first entity of the search result
	 * */
	E find(E entidade)throws Exception;
	
	/**
	* Search by filled attributes
	* */
	List<E> findAll(E entity)throws Exception;
	
	/**
	* An entity persists
	* */
	void persist(E entity)throws Exception;
	
	/**
	* Updates an entity
	* */
	E merge(E entity)throws Exception;
	
	/**
	* Remove an entity
	* */
	void remove(E entity)throws Exception;
	
	/**
	* Returns a list of all persisted entities
	* */
	List<E> listAll(Class<? extends ITEntity> entity)throws Exception;
	
	/**
	* Returns a paginated list
	* */
	List<E> pageAll(E entity, int firstResult, int maxResult, boolean orderByAsc)throws Exception;

	/**
	* Returns a paginated search
	* */
	List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception;

	/**
	* Returns the number of records found
	* */
	Integer countFindAll(E entity, boolean containsAnyKeyWords)throws Exception;

	/**
	* Returns the number of registered records
	* */
	Long countAll(Class<? extends ITEntity> entity)throws Exception;
}
