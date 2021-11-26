package com.tedros.core.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface TFileEntityController extends ITSecureEjbController<TFileEntity>{

	public TResult<TFileEntity> loadBytes(TAccessToken token, TFileEntity entity);

	public TResult<TFileEntity> findByIdWithBytesLoaded(TAccessToken token, TFileEntity entity);
	
	public TResult<List<TFileEntity>> find(TAccessToken token, List<String> owner, List<String> ext, Long maxSize, boolean loaded);
	
}
