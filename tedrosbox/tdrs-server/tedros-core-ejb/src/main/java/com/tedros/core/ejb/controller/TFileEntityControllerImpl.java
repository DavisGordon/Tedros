package com.tedros.core.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.service.TFileEntityService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;

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
			return new TResult<>(EnumResult.SUCESS, l);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	@Override
	public TResult<TFileEntity> findByIdWithBytesLoaded(TAccessToken token, TFileEntity entity) {
		try{
			entity = serv.findById(entity);
			
			serv.loadBytes(entity);
			
			return new TResult<TFileEntity>(EnumResult.SUCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TFileEntity>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	
	@Override
	public TResult<TFileEntity> loadBytes(TAccessToken token, TFileEntity entity) {
		try{
			if(entity==null || entity.getByteEntity()==null || entity.getByteEntity().getId()==null)
				throw new IllegalArgumentException("ERROR: method loadBytes from TFileEntityService cant receive null for byteEntity.id at TFileEntity parameter!");
			
			serv.loadBytes(entity);
			
			return new TResult<TFileEntity>(EnumResult.SUCESS, entity);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<TFileEntity>(EnumResult.ERROR, e.getMessage());
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
