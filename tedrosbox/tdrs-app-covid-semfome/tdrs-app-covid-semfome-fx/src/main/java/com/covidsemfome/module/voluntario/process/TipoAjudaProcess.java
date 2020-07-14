/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.voluntario.process;

import java.util.List;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The person CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class TipoAjudaProcess extends TEntityProcess<TipoAjuda> {

	public TipoAjudaProcess() throws TProcessException {
		super(TipoAjuda.class, "ITipoAjudaControllerRemote", true);
	}


	@Override
	public void execute(List<TResult<TipoAjuda>> resultados) {
		
	}

}
