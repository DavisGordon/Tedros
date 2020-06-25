package com.tedros.ejb.base.bo;

import java.util.List;

import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

public abstract class TGenericBO<E extends ITEntity> implements ITGenericBO<E> {

	public abstract ITGenericEAO<E> getEao();
	
	/**
	 * Retorna uma entidade pelo seu id
	 * */
	public E find(E entidade)throws Exception{
		return getEao().find(entidade);
	}
	
	
	public List<E> findAll(E entidade)throws Exception{
		return getEao().findAll(entidade);
	}
	
	/**
	 * Salva uma entidade
	 * */
	public E save(E entidade)throws Exception{
		if(entidade.isNew()){
			getEao().persist(entidade);
			return entidade;
		}else
			return getEao().merge(entidade);
	}
	/**
	 * Remove uma entidade
	 * */
	public void remove(E entidade)throws Exception{
		getEao().remove(entidade);
	}
	/**
	 * Retorna uma lista com todas as entidades persistidas
	 * */
	public List<E> listAll(Class<? extends ITEntity> entidade)throws Exception{
		return getEao().listAll(entidade);
	}
	/**
	 * Retorna uma lista paginada
	 * */
	public List<E> pageAll(Class<? extends ITEntity> entidade, int firstResult, int maxResult)throws Exception{
		return getEao().pageAll(entidade, firstResult, maxResult);
	}
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(Class<? extends ITEntity> entidade)throws Exception{
		return getEao().countAll(entidade);
	}
	
	
}
