package com.tedros.fxapi.process;

import com.tedros.fxapi.exception.TProcessException;

@SuppressWarnings("rawtypes")
public final class TDisabledCrudProcess extends TEntityProcess {

	@SuppressWarnings("unchecked")
	public TDisabledCrudProcess() throws TProcessException {
		super(null, "");
		
	}

}
