/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.exception.MailingWarningException;
import com.covidsemfome.ejb.service.MailingService;
import com.covidsemfome.model.Mailing;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TRemoteSecurity;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TRemoteSecurity
@Stateless(name="IMailingController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class MailingServiceController extends TSecureEjbController<Mailing> implements IMailingController, ITSecurity{
	
	@EJB
	private MailingService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public TResult<Mailing> save(TAccessToken token, Mailing m) {
		
		try{
			serv.enviar(m);
			return new TResult<>(EnumResult.SUCESS, true, "Emails enviado com sucesso!", m);
				
		}catch(EJBException e){
			if(e.getCause() instanceof MailingWarningException)
				return new TResult<>(EnumResult.WARNING, true, e.getCause().getMessage(), m);
			else
				return new TResult<>(EnumResult.ERROR, true, "Um erro impediu o envio de email para este mailing!", m);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, true, "Um erro impediu o envio de email para este mailing!", m);
		}
		
	}


	@Override
	public ITEjbService<Mailing> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

}
