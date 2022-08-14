package org.tedros.core.controller;

import java.util.List;

import javax.ejb.Remote;

import org.tedros.common.model.TFileEntity;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

@Remote
public interface TFileEntityController extends ITSecureEjbController<TFileEntity>{

	public TResult<TFileEntity> loadBytes(TAccessToken token, TFileEntity entity);

	public TResult<TFileEntity> findByIdWithBytesLoaded(TAccessToken token, TFileEntity entity);
	
	public TResult<List<TFileEntity>> find(TAccessToken token, List<String> owner, List<String> ext, Long maxSize, boolean loaded);
	
}
