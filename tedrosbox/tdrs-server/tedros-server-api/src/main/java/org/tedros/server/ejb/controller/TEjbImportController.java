package org.tedros.server.ejb.controller;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.controller.ITEjbImportController;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITImportModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;
import org.tedros.server.service.ITEjbImportService;

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
