/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.termoAdesao.process;

import java.util.List;

import com.covidsemfome.model.TermoAdesao;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The TermoAdesao CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class TermoAdesaoProcess extends TEntityProcess<TermoAdesao> {

	public TermoAdesaoProcess() throws TProcessException {
		super(TermoAdesao.class, "ITermoAdesaoControllerRemote");
		
		
	}


	@Override
	public void execute(List<TResult<TermoAdesao>> resultados) {
		
	}

}
