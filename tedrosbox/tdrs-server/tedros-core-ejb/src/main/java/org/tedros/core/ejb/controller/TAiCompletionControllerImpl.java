/**
 * 
 */
package org.tedros.core.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TCompletionRequest;
import org.tedros.core.ai.model.TCompletionResult;
import org.tedros.core.controller.TAiCompletionController;
import org.tedros.core.ejb.service.TAiCompletionService;
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
@Stateless(name="TAiCompletionController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAiCompletionControllerImpl extends TSecureEjbController<TAiCompletion>  implements TAiCompletionController, ITSecurity {

	@EJB
	private TAiCompletionService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TSecurityService securityService;
	
	/* (non-Javadoc)
	 * @see org.tedros.core.controller.TAiController#completion(org.tedros.server.security.TAccessToken, org.tedros.core.ai.model.TCompletionRequest)
	 */
	@Override
	public TResult<TCompletionResult> completion(TAccessToken token, TCompletionRequest request) {
		try {
			TUser user = this.securityService.getUser(token);
			request.setUser(user.getName());
			TCompletionResult r = serv.completion(request);
			return new TResult<>(TState.SUCCESS, r);
		}catch(Exception ex) {
			return this.processException(ex);
		}
	}

	/* (non-Javadoc)
	 * @see org.tedros.core.controller.TAiController#createImage(org.tedros.server.security.TAccessToken, org.tedros.core.ai.model.TCreateImageRequest)
	 
	@Override
	public TResult<TImageResult> createImage(TAccessToken token, TCreateImageRequest request) {
		try {
			TUser user = this.securityService.getUser(token);
			request.setUser(user.getName());
			TImageResult r = serv.createImage(request);
			return new TResult<>(TState.SUCCESS, r);
		}catch(Exception ex) {
			return this.processException(ex);
		}
	}
	*/

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
	protected ITEjbService<TAiCompletion> getService() {
		return serv;
	}
	

}
