package org.tedros.server.controller;

import java.util.List;
import java.util.Map;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

public interface ITSecureEjbController<E extends ITEntity> extends ITBaseController {
	/**
	 * Search for entities
	 * */
	TResult<List<E>> search(TAccessToken token, TSelect<E> sel);

	/**
	 * Search for entities
	 * */
	TResult<Map<String, Object>> search(TAccessToken token, TSelect<E> sel, int firstResult, int maxResult);
	
	/**
	 * Find an entity by id
	 * */
	TResult<E> findById(TAccessToken token, E entidade)throws Exception;
	
	/**
	 * Returns the first entity searched with attributes filled in
	 * */
	TResult<E> find(TAccessToken token, E entidade);
	
	/**
	 * Search for entities with attributes filled in
	 * */
	TResult<List<E>> findAll(TAccessToken token, E entity);
	
	/**
	 *  Save an entity
	 * */
	TResult<E> save(TAccessToken token, E entidade);
	
	/**
	 *  Remove/delete an entity
	 * */
	TResult<E> remove(TAccessToken token, E entidade);
	
	/**
	 * List all typed entities
	 * */
	TResult<List<E>> listAll(TAccessToken token, Class<? extends ITEntity> entidadeClass);
	
	/**
	 * * Paginate all typed entity
	 * */
	TResult<Map<String, Object>> pageAll(TAccessToken token, E entidade, int firstResult, int maxResult, boolean orderByAsc);
	
	/**
	 * Find and page the result
	 * */
	TResult<Map<String, Object>> findAll(TAccessToken token, E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception;

}
