/**
 * 
 */
package com.solidarity.module.cozinha.process;

import com.solidarity.model.Cozinha;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class CozinhaOptionProcess extends TOptionsProcess<Cozinha> {

	/**
	 * @param entityType
	 * @param serviceJndiName
	 * @param remoteMode
	 * @throws TProcessException
	 */
	public CozinhaOptionProcess()
			throws TProcessException {
		super(Cozinha.class, "ICozinhaControllerRemote");
	}

}
