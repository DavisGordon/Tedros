/**
 * 
 */
package org.somos.module.pessoa.model;

import org.somos.model.TipoAjuda;

import com.tedros.fxapi.builder.TGenericBuilder;

/**
 * @author Davis Gordon
 *
 */
public class TipoAjudaExampleBuilder extends TGenericBuilder<TipoAjuda> {

	/**
	 * 
	 */
	public TipoAjudaExampleBuilder() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public TipoAjuda build() {
		TipoAjuda ex = new TipoAjuda();
		ex.setTipoPessoa("PF");
		return ex;
	}

}
