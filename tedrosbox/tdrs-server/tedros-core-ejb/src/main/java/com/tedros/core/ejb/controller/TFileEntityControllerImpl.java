package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.tedros.core.ejb.service.TFileEntityServiceImpl;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.global.model.TFileEntity;


@Stateless(name="TFileEntityController")
public class TFileEntityControllerImpl extends TEjbController<TFileEntity> implements	TFileEntityController {

	@EJB
	private TFileEntityServiceImpl serv;
	
	
	@Override
	public TResult<TFileEntity> loadBytes(TFileEntity entity) {
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


}
