package com.tedros.core.ejb.service;

import javax.ejb.Remote;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.global.model.TFileEntity;

@Remote
public interface TFileEntityService extends ITEjbService<TFileEntity>{

	public TResult<TFileEntity> loadBytes(TFileEntity entity);
	
	
}
