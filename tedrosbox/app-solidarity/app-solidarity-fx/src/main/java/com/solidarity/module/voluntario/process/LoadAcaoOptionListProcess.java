/**
 * 
 */
package com.solidarity.module.voluntario.process;

import com.solidarity.model.Acao;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadAcaoOptionListProcess extends TOptionsProcess<Acao> {

	public LoadAcaoOptionListProcess() throws TProcessException {
		super(Acao.class, "IAcaoControllerRemote");
	}

}
