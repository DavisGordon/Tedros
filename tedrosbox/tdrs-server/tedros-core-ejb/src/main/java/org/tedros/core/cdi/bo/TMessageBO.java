package org.tedros.core.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.core.cdi.eao.TMessageEao;
import org.tedros.core.message.model.TMessage;
import org.tedros.server.cdi.bo.TGenericBO;

@RequestScoped
public class TMessageBO extends TGenericBO<TMessage> {
	
	@Inject
	private TMessageEao eao;
	
	@Override
	public TMessageEao getEao() {
		return eao;
	}


}
