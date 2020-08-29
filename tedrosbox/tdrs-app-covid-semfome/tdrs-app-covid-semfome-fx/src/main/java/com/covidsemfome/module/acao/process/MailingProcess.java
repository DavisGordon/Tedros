/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.acao.process;

import java.util.List;

import com.covidsemfome.model.Mailing;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The mailing Process
 *
 * @author Davis Gordon
 *
 */
public class MailingProcess extends TEntityProcess<Mailing> {

	public MailingProcess() throws TProcessException {
		super(Mailing.class, "IMailingControllerRemote");
	}


	@Override
	public void execute(List<TResult<Mailing>> resultados) {
		
	}

}
