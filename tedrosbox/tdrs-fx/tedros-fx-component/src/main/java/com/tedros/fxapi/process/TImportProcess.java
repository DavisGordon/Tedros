/**
 * 
 */
package com.tedros.fxapi.process;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.naming.NamingException;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.controller.ITEjbImportController;
import com.tedros.ejb.base.model.ITImportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TImportProcess<M extends ITImportModel> extends TProcess<TResult<M>> {

	private M model;
	private TImportProcessEnum action;
	private String serviceJndiName;
	
	
	public TImportProcess(String serviceJndiName) throws TProcessException {
		setAutoStart(true);
		this.serviceJndiName = serviceJndiName;
	}
	
	public void importFile(M model){
		this.model = model;
		this.action = TImportProcessEnum.IMPORT;
	}
	
	public void getImportRules(){
		this.action = TImportProcessEnum.GET_RULES;	
	}
	
	
	protected TTaskImpl<TResult<M>> createTask() {
		
		return new TTaskImpl<TResult<M>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
			@SuppressWarnings("unchecked")
			protected TResult<M> call() throws IOException, MalformedURLException {
        		ServiceLocator loc = ServiceLocator.getInstance();
        		TResult<M> resultado = null;
        		try {
        			TUser user = TedrosContext.getLoggedUser();
        			ITEjbImportController service = (ITEjbImportController) loc.lookup(serviceJndiName);
        			switch(action) {
        			case IMPORT:
        				resultado = service.importFile(user.getAccessToken(), model.getFile());
        				break;
        			case GET_RULES:
        				resultado = service.getImportRules(user.getAccessToken());
        				break;
        			}
	    		} catch(NamingException e){
	    			setException( new TProcessException(e, e.getMessage(), "The service is not available!"));
	    			e.printStackTrace();
	    		}catch (Exception e) {
					setException(e);
					e.printStackTrace();
				} finally {
					loc.close();
				}
        	    return resultado;
        	}
		};
	}
	

	/**
	 * @return the model
	 */
	protected M getModel() {
		return model;
	}

}
