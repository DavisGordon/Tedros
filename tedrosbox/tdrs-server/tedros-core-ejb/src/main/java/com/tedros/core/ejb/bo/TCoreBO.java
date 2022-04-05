/**
 * 
 */
package com.tedros.core.ejb.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.core.ejb.eao.TCoreEAO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TCoreBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TCoreEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}
