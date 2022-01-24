/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.module.tipoAjuda.process;

import org.somos.model.TipoAjuda;

import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The person CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class TipoAjudaProcess extends TEntityProcess<TipoAjuda> {

	public TipoAjudaProcess() throws TProcessException {
		super(TipoAjuda.class, "ITipoAjudaControllerRemote");
	}
}
