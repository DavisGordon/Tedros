/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.editorweb.server.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.editorweb.server.bo.TEntityBO;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@LocalBean
@Stateless(name="TEntityService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TEntityService<E extends ITEntity> extends TEjbService<E> /*implements ITRunService<E>*/ {
	
	@Inject
	private TEntityBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
/*
	@Override
	public ITRunBO<E> getRunBO() {
		return bo;
	}

	@Override
	public void runNonTx(Consumer<TEntityService<E>> f) {
		f.accept(this);
	}

	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void runTx(Consumer<TEntityService<E>> f) {
		f.accept(this);
	}
	*/

}
