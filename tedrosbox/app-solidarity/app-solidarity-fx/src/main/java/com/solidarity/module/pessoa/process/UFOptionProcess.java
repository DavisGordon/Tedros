/**
 * 
 */
package com.solidarity.module.pessoa.process;

import com.solidarity.model.UF;
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
