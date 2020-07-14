package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.global.model.TFileEntity;

@Remote
public interface TFileEntityController extends ITEjbController<TFileEntity>{

	public TResult<TFileEntity> loadBytes(TFileEntity entity);
	
	
}
