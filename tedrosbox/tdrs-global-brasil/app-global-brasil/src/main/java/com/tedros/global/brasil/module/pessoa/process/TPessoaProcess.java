/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.module.pessoa.process;

import java.util.List;

import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;
import com.tedros.global.brasil.model.Pessoa;

/**
 * The person CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class TPessoaProcess extends TEntityProcess<Pessoa> {

	public TPessoaProcess() throws TProcessException {
		super(Pessoa.class, "TPessoaServiceRemote", true);
	}


	@Override
	public void execute(List<TResult<Pessoa>> resultados) {
		
	}

}
