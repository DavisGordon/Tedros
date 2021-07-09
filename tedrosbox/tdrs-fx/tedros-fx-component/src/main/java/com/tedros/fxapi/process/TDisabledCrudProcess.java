package com.tedros.fxapi.process;

import java.util.List;

import com.tedros.fxapi.exception.TProcessException;

@SuppressWarnings("rawtypes")
public class TDisabledCrudProcess extends TEntityProcess {

	public TDisabledCrudProcess() throws TProcessException {
		super(null, "", false);
		
	}

	@Override
	public void execute(List resultados) {
		// TODO Auto-generated method stub

	}

}
