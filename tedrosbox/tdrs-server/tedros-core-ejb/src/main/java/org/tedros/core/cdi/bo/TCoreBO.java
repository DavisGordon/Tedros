/**
 * 
 */
package org.tedros.core.cdi.bo;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.tedros.core.cdi.eao.TCoreEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TCoreBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TCoreEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}
