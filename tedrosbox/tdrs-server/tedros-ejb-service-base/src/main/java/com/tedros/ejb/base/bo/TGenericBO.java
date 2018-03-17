package com.tedros.ejb.base.bo;

import java.util.List;

import javax.persistence.EntityManager;

import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

public abstract class TGenericBO<E extends ITEntity> implements ITGenericBO<E> {

	public abstract ITGenericEAO<E> getEao();
	
	/**
	 * Retorna uma entidade pelo seu id
	 * */
	public E find(EntityManager em, E entidade)throws Exception{
		return getEao().find(em, entidade);
	}
	
	
	public List<E> findAll(EntityManager em, E entidade)throws Exception{
		return getEao().findAll(em, entidade);
	}
	
	/**
	 * Salva uma entidade
	 * */
	public E save(EntityManager em, E entidade)throws Exception{
		if(entidade.isNew()){
			getEao().persist(em, entidade);
			return entidade;
		}else
			return getEao().merge(em, entidade);
	}
	/**
	 * Remove uma entidade
	 * */
	public void remove(EntityManager em, E entidade)throws Exception{
		getEao().remove(em, entidade);
	}
	/**
	 * Retorna uma lista com todas as entidades persistidas
	 * */
	public List<E> listAll(EntityManager em, Class<? extends ITEntity> entidade)throws Exception{
		return getEao().listAll(em, entidade);
	}
	/**
	 * Retorna uma lista paginada
	 * */
	public List<E> pageAll(EntityManager em, Class<? extends ITEntity> entidade, int firstResult, int maxResult)throws Exception{
		return getEao().pageAll(em, entidade, firstResult, maxResult);
	}
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(EntityManager em, Class<? extends ITEntity> entidade)throws Exception{
		return getEao().countAll(em, entidade);
	}
	
	
}
