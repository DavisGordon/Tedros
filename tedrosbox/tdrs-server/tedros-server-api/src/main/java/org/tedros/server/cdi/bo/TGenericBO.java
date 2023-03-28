package org.tedros.server.cdi.bo;

import java.util.List;

import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

public abstract  class TGenericBO<E extends ITEntity> implements ITGenericBO<E> {

	public abstract ITGenericEAO<E> getEao();
	/**
	 * Search for entities
	 * */
	public List<E> search(TSelect<E> sel){
		return getEao().search(sel);
	}
	
	/**
	 * Retorna uma entidade pelo seu id
	 * */
	public E findById(E entidade)throws Exception{
		return getEao().findById(entidade);
	}
	
	/**
	 * Retorna a primeira entidade pesquisada com os atributos preenchidos
	 * */
	public E find(E entidade)throws Exception{
		return getEao().find(entidade);
	}
	
	/**
	 * Pesquisa pelos atributos preenchidos
	 * */
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
	public List<E> pageAll(E entidade, int firstResult, int maxResult, boolean orderByAsc)throws Exception{
		return getEao().pageAll(entidade, firstResult, maxResult, orderByAsc);
	}
	/**
	 * Retorna a quantidade de registros cadastrados
	 * */
	public Long countAll(Class<? extends ITEntity> entidade)throws Exception{
		return getEao().countAll(entidade);
	}

	@Override
	public List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) throws Exception {
		return getEao().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
	}

	@Override
	public Integer countFindAll(E entity, boolean containsAnyKeyWords) throws Exception {
		return getEao().countFindAll(entity, containsAnyKeyWords);
	}
	
	
}
