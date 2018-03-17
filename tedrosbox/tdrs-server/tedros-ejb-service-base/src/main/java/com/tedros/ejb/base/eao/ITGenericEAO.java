package com.tedros.ejb.base.eao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tedros.ejb.base.entity.ITEntity;

public interface ITGenericEAO<E extends ITEntity> {

	/**
	 * Retorna uma entidade pelo seu id
	 * */
	public E find(EntityManager em, E entidade)throws Exception;
	
	/**
	 * Pesquisa pelos atributos preenchidos
	 * */
	public List<E> findAll(EntityManager em, E entity)throws Exception;
	/**
	 * Persiste uma entidade
	 * */
	public void persist(EntityManager em, E entidade)throws Exception;
	/**
	 * Atualiza uma entidade
	 * */
	public E merge(EntityManager em, E entidade)throws Exception;
	/**
	 * Remove uma entidade
	 * */
	public void remove(EntityManager em, E entidade)throws Exception;
	/**
	 * Retorna uma lista com todas as entidades persistidas
	 * */
	public List<E> listAll(EntityManager em, Class<? extends ITEntity> entidade)throws Exception;
	/**
	 * Retorna uma lista paginada
	 * */	
	public List<E> pageAll(EntityManager em, Class<? extends ITEntity> entidade, int firstResult, int maxResult)throws Exception;
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(EntityManager em, Class<? extends ITEntity> entidade)throws Exception;
	
}
