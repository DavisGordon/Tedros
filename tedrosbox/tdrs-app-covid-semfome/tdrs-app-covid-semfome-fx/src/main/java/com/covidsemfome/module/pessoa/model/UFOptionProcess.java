/**
 * 
 */
package com.covidsemfome.module.pessoa.model;

import com.covidsemfome.model.UF;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class UFOptionProcess extends TOptionsProcess<UF> {

	public UFOptionProcess()
			throws TProcessException {
		super(UF.class, "IUFControllerRemote");
	}

}
