package com.tedros.core.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.core.ejb.eao.TProfileEao;
import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.bo.TGenericBO;

@RequestScoped
public class TProfileBO extends TGenericBO<TProfile> {
	
	@Inject
	private TProfileEao eao;
	
	@Override
	public TProfileEao getEao() {
		return eao;
	}

}
