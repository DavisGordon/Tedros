/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.module.termoAdesao.process;

import com.covidsemfome.model.TermoAdesao;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The TermoAdesao CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class TermoAdesaoProcess extends TEntityProcess<TermoAdesao> {

	public TermoAdesaoProcess() {
		super(TermoAdesao.class, "ITermoAdesaoControllerRemote");
	}
}
