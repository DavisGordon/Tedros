/**
 * 
 */
package org.tedros.core.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.ai.model.image.TCreateImageRequest;
import org.tedros.core.ai.model.image.TImageResult;
import org.tedros.core.controller.TAiCreateImageController;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.ejb.service.TAiCreateImageService;
import org.tedros.core.ejb.service.TSecurityService;
import org.tedros.core.security.model.TUser;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */

@TSecurityInterceptor
@Stateless(name="TAiCreateImageController")
@TBeanSecurity(@TBeanPolicie(id=DomainApp.CR_IMAGE_TEROS_FORM_ID, 
policie= {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS}))
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAiCreateImageControllerImpl extends TSecureEjbController<TAiCreateImage>  implements TAiCreateImageController, ITSecurity {

	@EJB
	private TAiCreateImageService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@EJB
	private TSecurityService securityService;
	
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
	protected ITEjbService<TAiCreateImage> getService() {
		return serv;
	}
	

}
