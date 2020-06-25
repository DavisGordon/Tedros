/**
 * 
 */
package com.tedros.core.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.core.ejb.eao.TFileEntityEao;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.global.model.TFileEntity;

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

}
