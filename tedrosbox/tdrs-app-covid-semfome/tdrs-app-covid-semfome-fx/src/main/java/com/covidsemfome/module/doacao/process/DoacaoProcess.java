/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.doacao.process;

import java.util.List;

import com.covidsemfome.model.Doacao;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class DoacaoProcess extends TEntityProcess<Doacao> {

	public DoacaoProcess() throws TProcessException {
		super(Doacao.class, "IDoacaoControllerRemote");
	}


	@Override
	public void execute(List<TResult<Doacao>> resultados) {
		
	}

}
