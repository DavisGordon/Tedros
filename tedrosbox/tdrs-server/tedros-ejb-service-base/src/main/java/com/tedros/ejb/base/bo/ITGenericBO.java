package com.tedros.ejb.base.bo;

import java.util.List;

import javax.persistence.EntityManager;

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
	public E find(EntityManager em, E entidade)throws Exception;
	
	/**
	 * Pesquisa pelos atributos preenchidos
	 * */
	public List<E> findAll(EntityManager em, E entity)throws Exception;
	
	/**
	 * Salva uma entidade
	 * */
	public E save(EntityManager em, E entidade)throws Exception;
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
