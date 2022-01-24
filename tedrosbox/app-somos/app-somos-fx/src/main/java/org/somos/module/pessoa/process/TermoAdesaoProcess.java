/**
 * 
 */
package org.somos.module.pessoa.process;

import org.somos.model.TermoAdesao;

import com.tedros.fxapi.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TermoAdesaoProcess extends TEntityProcess<TermoAdesao> {

	public TermoAdesaoProcess() {
		super(TermoAdesao.class, "ITermoAdesaoControllerRemote");
	}

}
