package com.tedros.ejb.base.controller;

import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

public interface ITSecureEjbController<E extends ITEntity> extends ITBaseController {
	
	/**
	 * Retorna uma entidade pelo seu id
	 * */
	@SuppressWarnings("rawtypes")
	public TResult findById(TAccessToken token, E entidade)throws Exception;
	
	/**
	 * Retorna a primeira entidade pesquisada com os atributos preenchidos
	 * */
	@SuppressWarnings("rawtypes")
	public TResult find(TAccessToken token, E entidade);
	
	/**
	 * Pesquisa pelos atributos preenchidos
	 * */
	public TResult<List<E>> findAll(TAccessToken token, E entity);
	
	/**
	 * Salva uma entidade
	 * */
	@SuppressWarnings("rawtypes")
	public TResult save(TAccessToken token, E entidade);
	/**
	 * Remove uma entidade
	 * */
	@SuppressWarnings("rawtypes")
	public TResult remove(TAccessToken token, E entidade);
	/**
	 * Retorna uma lista com todas as entidades persistidas
	 * */
	@SuppressWarnings("rawtypes")
	public TResult listAll(TAccessToken token, Class<? extends ITEntity> entidadeClass);
	/**
	 * Retorna uma lista paginada
	 * */
	@SuppressWarnings("rawtypes")
	public TResult pageAll(TAccessToken token, E entidade, int firstResult, int maxResult, boolean orderByAsc);
	
	/**
	 * Retorna uma pesquisa paginada
	 * */
	@SuppressWarnings("rawtypes")
	public TResult findAll(TAccessToken token, E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception;

}
