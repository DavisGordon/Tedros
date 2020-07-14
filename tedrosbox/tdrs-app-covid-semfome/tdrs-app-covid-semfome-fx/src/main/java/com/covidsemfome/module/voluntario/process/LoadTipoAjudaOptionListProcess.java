/**
 * 
 */
package com.covidsemfome.module.voluntario.process;

import java.util.List;
import java.util.Map;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadTipoAjudaOptionListProcess extends TOptionsProcess {

	public LoadTipoAjudaOptionListProcess() throws TProcessException {
		super(TipoAjuda.class, "ITipoAjudaControllerRemote", true);
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
