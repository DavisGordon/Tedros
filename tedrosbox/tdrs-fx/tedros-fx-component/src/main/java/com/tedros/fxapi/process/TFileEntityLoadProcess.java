package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.ejb.controller.TFileEntityController;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;

/**
 * A process to load a file from application server
 * 
 * 
 * @author Davis Gordon
 * */
public class TFileEntityLoadProcess extends TProcess<TResult<TFileEntity>>{
	
	private String jndiService ="TFileEntityControllerRemote";
	
	private TFileEntity value;
	
	public TFileEntityLoadProcess() throws TProcessException {
		setAutoStart(true);
	}
	
	public void load(TFileEntity entidade){
		value = entidade;
	}
	@Override
	protected TTaskImpl<TResult<TFileEntity>> createTask() {
		
		return new TTaskImpl<TResult<TFileEntity>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
			protected TResult<TFileEntity> call() throws IOException, MalformedURLException {
        		ServiceLocator loc = ServiceLocator.getInstance();
        		TResult<TFileEntity> result = null;
        		try {
        			TUser user = TedrosContext.getLoggedUser();
        			TFileEntityController service = loc.lookup(jndiService);
	        		if(service!=null){
	        			result = service.loadBytes(user.getAccessToken(), value);
	        		}
        		} catch (Exception e) {
					setException(e);
					e.printStackTrace();
				} finally {
					loc.close();
				}
        	    return result;
        	}
		};
	}
	
	

}
