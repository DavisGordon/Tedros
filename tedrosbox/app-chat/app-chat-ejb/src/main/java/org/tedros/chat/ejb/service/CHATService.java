/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.chat.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.chat.cdi.bo.CHATBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.ejb.service.TEjbService;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@LocalBean
@Stateless(name="CHATService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CHATService<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private CHATBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}
