package com.tedros.core.ejb.bo;

import com.tedros.core.ejb.eao.TProfileEao;
import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.bo.TGenericBO;

public final class TProfileBO extends TGenericBO<TProfile> {
	
	private TProfileEao eao = new TProfileEao();
	
	@Override
	public TProfileEao getEao() {
		return eao;
	}

}
