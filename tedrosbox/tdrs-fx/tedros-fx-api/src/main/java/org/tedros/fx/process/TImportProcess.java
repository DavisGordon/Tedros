/**
 * 
 */
package org.tedros.fx.process;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.naming.NamingException;

import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.exception.TProcessException;
import org.tedros.server.controller.ITEjbImportController;
import org.tedros.server.model.ITImportModel;
import org.tedros.server.result.TResult;

/**
 * The process to import files.
 * The target entity must be of the ITImportModel type 
 * and the target service of the ITEjbImportController
 * @author Davis Gordon
 *
 * @param <M>
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
	
	/**
	 * Upload the model with the file to import
	 * @param model
	 */
	public void importFile(M model){
		this.model = model;
		this.action = TImportProcessEnum.IMPORT;
	}
	
	/**
	 * Get the import rules
	 */
	public void getImportRules(){
		this.action = TImportProcessEnum.GET_RULES;	
	}
	
	@Override
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
