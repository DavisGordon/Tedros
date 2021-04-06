package com.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.service.TFileEntityService;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;


@Stateless(name="TFileEntityController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TFileEntityControllerImpl extends TEjbController<TFileEntity> implements	TFileEntityController {

	@EJB
	private TFileEntityService serv;
	
	
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
