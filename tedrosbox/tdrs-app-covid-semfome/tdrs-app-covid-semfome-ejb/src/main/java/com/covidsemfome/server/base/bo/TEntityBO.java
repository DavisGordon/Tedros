/**
 * 
 */
package com.covidsemfome.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.covidsemfome.server.base.eao.TEntityEAO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TEntityBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TEntityEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}
