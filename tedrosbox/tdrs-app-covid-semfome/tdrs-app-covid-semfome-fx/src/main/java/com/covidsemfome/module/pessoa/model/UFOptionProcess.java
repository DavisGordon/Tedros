/**
 * 
 */
package com.covidsemfome.module.pessoa.model;

import java.util.List;
import java.util.Map;

import com.covidsemfome.model.UF;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class UFOptionProcess extends TOptionsProcess {

	/**
	 * @param entityType
	 * @param serviceJndiName
	 * @param remoteMode
	 * @throws TProcessException
	 */
	public UFOptionProcess()
			throws TProcessException {
		super(UF.class, "IUFControllerRemote", true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TResult<Object>> doFilter(Map<String, Object> objects) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
