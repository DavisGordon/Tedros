/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.pessoa.process;

import java.util.List;

import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The person CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class TPessoaProcess extends TEntityProcess<Pessoa> {

	public TPessoaProcess() throws TProcessException {
		super(Pessoa.class, "IPessoaControllerRemote");
	}


	@Override
	public void execute(List<TResult<Pessoa>> resultados) {
		
	}

}
