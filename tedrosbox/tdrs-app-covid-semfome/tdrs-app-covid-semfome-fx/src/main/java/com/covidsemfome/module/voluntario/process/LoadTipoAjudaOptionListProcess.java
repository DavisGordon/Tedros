/**
 * 
 */
package com.covidsemfome.module.voluntario.process;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadTipoAjudaOptionListProcess extends TOptionsProcess<TipoAjuda> {

	public LoadTipoAjudaOptionListProcess() throws TProcessException {
		super(TipoAjuda.class, "ITipoAjudaControllerRemote");
	}
}
