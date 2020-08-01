/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.covidsemfome.ejb.exception.MailingWarningException;
import com.covidsemfome.ejb.service.MailingService;
import com.covidsemfome.model.Mailing;
import com.tedros.ejb.base.controller.TEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IMailingController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class MailingServiceController extends TEjbController<Mailing> implements IMailingController{
	
	@EJB
	private MailingService serv;
	
	@Override
	public TResult<Mailing> save(Mailing m) {
		
		try{
			serv.enviar(m);
			return new TResult<>(EnumResult.SUCESS, true, "Emails enviado com sucesso!", m);
				
		}catch(MailingWarningException e){
			return new TResult<>(EnumResult.WARNING, true, e.getMessage(), m);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, true, "Um erro impediu o envio de email para este mailing!", m);
		}
		
	}


	@Override
	public ITEjbService<Mailing> getService() {
		return serv;
	}
	

}
