/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.doador.process;

import java.util.List;

import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;
import com.covidsemfome.model.Doador;

/**
 * The CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class DoadorProcess extends TEntityProcess<Doador> {

	public DoadorProcess() throws TProcessException {
		super(Doador.class, "TDoadorServiceRemote", true);
	}


	@Override
	public void execute(List<TResult<Doador>> resultados) {
		
	}

}
