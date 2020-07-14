package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TFileEntityBO;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.global.model.TFileEntity;

@Local
@Stateless(name="TFileEntityService")
public class TFileEntityServiceImpl extends TEjbService<TFileEntity>  {

	@Inject
	private TFileEntityBO bo;
	
	
	
	@Override
	public ITGenericBO<TFileEntity> getBussinesObject() {
		return bo;
	}

	public void loadBytes(TFileEntity entity) {
		 bo.loadBytes(entity);
	}


}
