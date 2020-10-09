/**
 * 
 */
package com.covidsemfome.module.cozinha.process;

import java.util.List;
import java.util.Map;

import com.covidsemfome.model.Cozinha;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class CozinhaOptionProcess extends TOptionsProcess {

	/**
	 * @param entityType
	 * @param serviceJndiName
	 * @param remoteMode
	 * @throws TProcessException
	 */
	public CozinhaOptionProcess()
			throws TProcessException {
		super(Cozinha.class, "ICozinhaControllerRemote", true);
	}


	/* (non-Javadoc)
	 * @see com.tedros.fxapi.process.TOptionsProcess#doFilter(java.util.Map)
	 */
	@Override
	public List<TResult<Object>> doFilter(Map<String, Object> objects) {
		// TODO Auto-generated method stub
		return null;
	}

}
