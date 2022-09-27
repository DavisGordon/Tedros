/**
 * 
 */
package org.tedros.chat.cdi.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.tedros.chat.cdi.eao.CHATEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class CHATBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private CHATEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}
