/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.voluntario.process;

import java.util.List;

import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The person CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class VoluntarioProcess extends TEntityProcess<Voluntario> {

	public VoluntarioProcess() throws TProcessException {
		super(Voluntario.class, "IVoluntarioControllerRemote");
	}


	@Override
	public void execute(List<TResult<Voluntario>> resultados) {
		
	}

}
