package com.tedros.fxapi.process;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.exception.TProcessException;

public abstract class TFilterProcess extends TOptionsProcess{

	public TFilterProcess(Class<? extends ITEntity> entityType, String serviceJndiName, boolean remoteMode) throws TProcessException {
		super(entityType, serviceJndiName, remoteMode);
	}	

}
