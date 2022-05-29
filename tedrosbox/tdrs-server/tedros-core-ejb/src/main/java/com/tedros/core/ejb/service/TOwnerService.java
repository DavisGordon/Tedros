package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TOwnerBO;
import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

@Local
@Stateless(name="TOwnerService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TOwnerService extends TEjbService<TOwner> {

	@Inject
	private TOwnerBO bo;
	
	@Override
	public ITGenericBO<TOwner> getBussinesObject() {
		return bo;
	}

	public TOwner getOwner() {
		return bo.getOwner();
	}
	
	
}
