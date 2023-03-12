/**
 * 
 */
package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.controller.TAiChatCompletionController;
import org.tedros.core.ejb.service.TAiChatCompletionService;
import org.tedros.core.ejb.service.TSecurityService;
import org.tedros.core.security.model.TUser;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */

@TSecurityInterceptor
@Stateless(name="TAiChatCompletionController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAiChatCompletionControllerImpl extends TSecureEjbController<TAiChatCompletion>  
implements TAiChatCompletionController, ITSecurity {

	@EJB
	private TAiChatCompletionService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TSecurityService securityService;
	
	@Override
	public TResult<TChatResult> chat(TAccessToken token, TChatRequest request) {
		try {
			TUser user = this.securityService.getUser(token);
			request.setUser(user.getName());
			TChatResult r = serv.chat(request);
			return new TResult<>(TState.SUCCESS, r);
		}catch(Exception ex) {
			return this.processException(ex);
		}
	}

	/**
	 * @return the securityController
	 */
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T processException(Exception e) {
		e.printStackTrace();
		if(e instanceof EJBException) {
			return (T) new TResult<>(TState.ERROR,true, e.getCause().getMessage());
		}else{
			return (T) new TResult<>(TState.ERROR, e.getMessage());
		}
	}

	@Override
	protected ITEjbService<TAiChatCompletion> getService() {
		return serv;
	}
	

}
