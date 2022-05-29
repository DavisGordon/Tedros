package com.tedros.core.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.core.ejb.eao.TOwnerEao;
import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.bo.TGenericBO;

@RequestScoped
public class TOwnerBO extends TGenericBO<TOwner> {
	
	@Inject
	private TOwnerEao eao;
	
	@Override
	public TOwnerEao getEao() {
		return eao;
	}

	public TOwner getOwner() {
		return eao.getOwner();
	}
	

}
