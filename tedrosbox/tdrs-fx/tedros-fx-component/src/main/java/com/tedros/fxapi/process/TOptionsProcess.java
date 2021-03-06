package com.tedros.fxapi.process;

import com.tedros.ejb.base.entity.ITEntity;

/**
 * A process to help the use of combobox 
 * and others components which need filter a list of domain.
 * 
 * @author Davis Gordon
 * */
public abstract class TOptionsProcess<E extends ITEntity> extends TEntityProcess<E> {

	public TOptionsProcess(Class<E> entityType, String serviceJndiName){
		super(entityType, serviceJndiName);
	}
	
}
