package com.tedros.ejb.base.bo;

import java.util.List;

import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

public interface ITGenericBO<E extends ITEntity> {

	/**
	 * Retorna um Entity Acess Object
	 * */
	public abstract ITGenericEAO<E> getEao();
	
	/**
	 * Retorna uma entidade pelo seu id
	 * */
	public E findById(E entidade)throws Exception;
	
	/**
	 * Retorna a primeira entidade pesquisada com os atributos preenchidos
	 * */
	public E find(E entidade)throws Exception;
	
	/**
	 * Pesquisa pelos atributos preenchidos
	 * */
	public List<E> findAll(E entity)throws Exception;
	
	/**
	 * Salva uma entidade
	 * */
	public E save(E entidade)throws Exception;
	/**
	 * Remove uma entidade
	 * */
	public void remove(E entidade)throws Exception;
	/**
	 * Retorna uma lista com todas as entidades persistidas
	 * */
	public List<E> listAll(Class<? extends ITEntity> entidade)throws Exception;
	/**
	 * Retorna uma lista paginada
	 * */
	public List<E> pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc)throws Exception;
	/**
	 * Retorna uma pesquisa paginada
	 * */
	public List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords)throws Exception;

	/**
	 * Retorna a quantidade de registros encontrados
	 * */
	public Integer countFindAll(E entity, boolean containsAnyKeyWords)throws Exception;
	
	
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(Class<? extends ITEntity> entidade)throws Exception;
}
