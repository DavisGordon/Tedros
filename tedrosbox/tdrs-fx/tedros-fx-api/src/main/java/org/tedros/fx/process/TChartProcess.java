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
import org.tedros.server.controller.ITEjbChartController;
import org.tedros.server.controller.TParam;
import org.tedros.server.model.ITChartModel;
import org.tedros.server.result.TResult;
import org.tedros.util.TLoggerUtil;

/**
 * The process to filter the chart data.
 * The target service must be of type ITEjbChartController
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TChartProcess extends TProcess<TResult<? extends ITChartModel>> {

	private TParam[] params;
	private String serviceJndiName;
	
	public TChartProcess(String serviceJndiName) throws TProcessException {
		setAutoStart(true);
		this.serviceJndiName = serviceJndiName;
	}
	
	/**
	 * Process the chart data with params
	 * @param params
	 */
	public void process(){
		this.params = new TParam[] {new TParam<String>(null)};
	}
	
	/**
	 * Process the chart data with params
	 * @param params
	 */
	public void process(TParam... params){
		this.params = params;
	}
	
	@Override
	protected TTaskImpl<TResult<? extends ITChartModel>> createTask() {
		
		return new TTaskImpl<TResult<? extends ITChartModel>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
			protected TResult<? extends ITChartModel> call() throws IOException, MalformedURLException {
        		ServiceLocator loc = ServiceLocator.getInstance();
        		TResult<? extends ITChartModel> resultado = null;
        		try {
        			TUser user = TedrosContext.getLoggedUser();
        			ITEjbChartController service = (ITEjbChartController) loc.lookup(serviceJndiName);
        			resultado = service.process(user.getAccessToken(), params);
        				
	    		} catch(NamingException e){
	    			setException( new TProcessException(e, e.getMessage(), "The service is not available!"));
	    			TLoggerUtil.error(getClass(), e.getMessage(), e);
	    		}catch (Exception e) {
					setException(e);
					TLoggerUtil.error(getClass(), e.getMessage(), e);
				} finally {
					loc.close();
				}
        	    return resultado;
        	}
		};
	}
	
	/**
	 * @return the params
	 */
	protected TParam[] getParams() {
		return params;
	}

}
