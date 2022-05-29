package com.tedros.ejb.base.controller;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITImportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TActionPolicie;
import com.tedros.ejb.base.security.TMethodPolicie;
import com.tedros.ejb.base.security.TMethodSecurity;
import com.tedros.ejb.base.service.ITEjbImportService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbImportController<E extends ITEntity> implements ITEjbImportController<E>, ITSecurity {

	
	public abstract ITEjbImportService<E> getService();
	
	@Override
	@SuppressWarnings("rawtypes")
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.SAVE})})
	public TResult getImportRules(TAccessToken token) {
		try{
			ITImportModel value = getService().getImportRules();
			return new TResult<ITImportModel>(TState.SUCCESS, value);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<String>(TState.ERROR,true, e.getMessage());
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.SAVE})})
	public TResult importFile(TAccessToken token, ITFileEntity entity) {
		try{
			List<E> res = getService().importFile(entity);
			return new TResult<List<E>>(TState.SUCCESS, res);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<String>(TState.ERROR,true, e.getMessage());
		}
	}
	
	

}
