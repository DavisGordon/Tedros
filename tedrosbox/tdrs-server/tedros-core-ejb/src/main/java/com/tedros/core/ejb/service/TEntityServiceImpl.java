/**
 * 
 */
package com.tedros.core.ejb.service;

import javax.ejb.Singleton;
import javax.ejb.Stateless;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Singleton
@Stateless(name = "TEntityService")
public class TEntityServiceImpl extends TEjbService<TEntity> implements TEntityService {

	private ITGenericBO<TEntity> bo = new TGenericBO<TEntity>() {

		private ITGenericEAO<TEntity> eao = new TGenericEAO<>();
		
		@Override
		public ITGenericEAO<TEntity> getEao() {
			return eao;
		}
	};
	
	/* (non-Javadoc)
	 * @see com.tedros.ejb.base.service.TEjbService#getBussinesObject()
	 */
	@Override
	public ITGenericBO<TEntity> getBussinesObject() {
		return bo;
	}

}
