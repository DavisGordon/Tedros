package org.tedros.fx.process;

import java.io.IOException;
import java.net.MalformedURLException;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TFileEntityController;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.exception.TProcessException;
import org.tedros.server.result.TResult;
import org.tedros.util.TLoggerUtil;

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
					TLoggerUtil.error(getClass(), e.getMessage(), e);
				} finally {
					loc.close();
				}
        	    return result;
        	}
		};
	}
	
	

}
