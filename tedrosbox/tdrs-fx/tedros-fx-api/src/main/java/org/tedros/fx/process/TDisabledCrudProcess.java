package org.tedros.fx.process;

import org.tedros.fx.exception.TProcessException;

@SuppressWarnings("rawtypes")
public final class TDisabledCrudProcess extends TEntityProcess {

	@SuppressWarnings("unchecked")
	public TDisabledCrudProcess() throws TProcessException {
		super(null, "");
		
	}

}
