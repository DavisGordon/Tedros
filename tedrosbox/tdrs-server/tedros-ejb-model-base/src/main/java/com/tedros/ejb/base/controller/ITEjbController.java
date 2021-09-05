package com.tedros.ejb.base.controller;

import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.result.TResult;

public interface ITEjbController<E extends ITEntity> extends ITBaseController {
	
	/**
	 * Retorna uma entidade pelo seu id
	 * */
	@SuppressWarnings("rawtypes")
	public TResult findById(E entidade)throws Exception;
	
	/**
	 * Retorna a primeira entidade pesquisada com os atributos preenchidos
	 * */
	@SuppressWarnings("rawtypes")
	public TResult find(E entidade);
	
	/**
	 * Pesquisa pelos atributos preenchidos
	 * */
	public TResult<List<E>> findAll(E entity);
	
	/**
	 * Salva uma entidade
	 * */
	@SuppressWarnings("rawtypes")
	public TResult save(E entidade);
	/**
	 * Remove uma entidade
	 * */
	@SuppressWarnings("rawtypes")
	public TResult remove(E entidade);
	/**
	 * Retorna uma lista com todas as entidades persistidas
	 * */
	@SuppressWarnings("rawtypes")
	public TResult listAll(Class<? extends ITEntity> entidadeClass);
	/**
	 * Retorna uma lista paginada
	 * */
	@SuppressWarnings("rawtypes")
	public TResult pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc);
	
	/**
	 * Retorna uma pesquisa paginada
	 * */
	@SuppressWarnings("rawtypes")
	public TResult findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception;

}
