/**
 * 
 */
package com.solidarity.module.pessoa.process;

import com.solidarity.model.TermoAdesao;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TermoAdesaoProcess extends TEntityProcess<TermoAdesao> {

	public TermoAdesaoProcess() {
		super(TermoAdesao.class, "ITermoAdesaoControllerRemote");
	}

}
