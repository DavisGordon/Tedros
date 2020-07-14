/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.acao.process;

import java.util.List;

import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The acao CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class AcaoProcess extends TEntityProcess<Acao> {

	public AcaoProcess() throws TProcessException {
		super(Acao.class, "IAcaoControllerRemote", true);
	}


	@Override
	public void execute(List<TResult<Acao>> resultados) {
		
	}

}
