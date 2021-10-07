/**
 * 
 */
package com.tedros.editorweb.server.bo;

import java.util.function.Consumer;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.editorweb.server.eao.TEntityEAO;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TEntityBO<E extends ITEntity> extends TGenericBO<E> implements ITRunBO<E> {

	@Inject
	private TEntityEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}
	
	public void run(Consumer<ITGenericBO<E>> f) {
		f.accept(this);
	}

}
