package org.tedros.core.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.controller.TFileEntityController;
import org.tedros.core.ejb.service.TFileEntityService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

@TSecurityInterceptor
@Stateless(name="TFileEntityController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TFileEntityControllerImpl extends TSecureEjbController<TFileEntity> implements TFileEntityController, ITSecurity{

	@EJB
	private TFileEntityService serv;
	
	@EJB
	private ITSecurityController security;
	
	public TResult<List<TFileEntity>> find(TAccessToken token, List<String> owner, List<String> ext, Long maxSize, boolean loaded){
		try {
			List<TFileEntity> l = serv.find(owner, ext, maxSize, loaded);
			return new TResult<>(TState.SUCCESS, l);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<TFileEntity> findByIdWithBytesLoaded(TAccessToken token, TFileEntity entity) {
		try{
			entity = serv.findById(entity);
			
			serv.loadBytes(entity);
			
			return new TResult<TFileEntity>(TState.SUCCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TFileEntity>(TState.ERROR, e.getMessage());
		}
	}
	
	
	@Override
	public TResult<TFileEntity> loadBytes(TAccessToken token, TFileEntity entity) {
		try{
			if(entity==null || entity.getByteEntity()==null || entity.getByteEntity().getId()==null)
				throw new IllegalArgumentException("ERROR: method loadBytes from TFileEntityService cant receive null for byteEntity.id at TFileEntity parameter!");
			
			serv.loadBytes(entity);
			
			return new TResult<TFileEntity>(TState.SUCCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TFileEntity>(TState.ERROR, e.getMessage());
		}
	}


	@Override
	public ITEjbService<TFileEntity> getService() {
		return serv;
	}


	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}


}
