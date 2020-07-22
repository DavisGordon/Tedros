/**
 * 
 */
package com.tedros.core.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.eao.TFileEntityEao;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TFileEntityBO extends TGenericBO<TFileEntity> {

	@Inject
	private TFileEntityEao eao;
	
	
	@Override
	public ITGenericEAO<TFileEntity> getEao() {
		return eao;
	}
	
	public void loadBytes(final TFileEntity entity) {
		eao.loadBytes(entity);
	}

}
