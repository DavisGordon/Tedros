/**
 * 
 */
package com.covidsemfome.module.voluntario.process;

import java.util.List;
import java.util.Map;

import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * @author Davis Gordon
 *
 */
public class LoadPessoaOptionListProcess extends TOptionsProcess {

	public LoadPessoaOptionListProcess() throws TProcessException {
		super(Pessoa.class, "IPessoaServiceRemote",true);
		// TODO Auto-generated constructor stub
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
