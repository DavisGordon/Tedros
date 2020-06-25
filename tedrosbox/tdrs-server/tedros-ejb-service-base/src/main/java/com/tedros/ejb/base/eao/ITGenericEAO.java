package com.tedros.ejb.base.eao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tedros.ejb.base.entity.ITEntity;

public interface ITGenericEAO<E extends ITEntity> {
	
	public EntityManager getEntityManager();

	/**
	 * Retorna uma entidade pelo seu id
	 * */
	public E find(E entidade)throws Exception;
	
	/**
	 * Pesquisa pelos atributos preenchidos
	 * */
	public List<E> findAll(E entity)throws Exception;
	/**
	 * Persiste uma entidade
	 * */
	public void persist(E entidade)throws Exception;
	/**
	 * Atualiza uma entidade
	 * */
	public E merge(E entidade)throws Exception;
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
	public List<E> pageAll(Class<? extends ITEntity> entidade, int firstResult, int maxResult)throws Exception;
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(Class<? extends ITEntity> entidade)throws Exception;
	
}
