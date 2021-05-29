/**
 * 
 */
package com.covidsemfome.module.voluntario.process;

import com.covidsemfome.model.Pessoa;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadPessoaOptionListProcess extends TOptionsProcess<Pessoa> {

	public LoadPessoaOptionListProcess() throws TProcessException {
		super(Pessoa.class, "IPessoaControllerRemote");
	}
}
