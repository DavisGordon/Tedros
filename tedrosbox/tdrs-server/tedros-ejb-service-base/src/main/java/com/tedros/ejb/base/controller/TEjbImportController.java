package com.tedros.ejb.base.controller;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITImportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbImportService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbImportController<E extends ITEntity> implements ITEjbImportController<E> {

	
	public abstract ITEjbImportService<E> getService();
	
	@Override
	@SuppressWarnings("rawtypes")
	public TResult getImportRules() {
		try{
			ITImportModel value = getService().getImportRules();
			return new TResult<ITImportModel>(EnumResult.SUCESS, value);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public TResult importFile(ITFileEntity entity) {
		try{
			getService().importFile(entity);;
			return new TResult<List<E>>(EnumResult.SUCESS);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<List<E>>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	

}
