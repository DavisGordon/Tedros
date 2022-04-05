/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TCoreBO;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="TCoreService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TCoreService<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private TCoreBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}
