/**
 * 
 */
package org.tedros.chat.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.chat.cdi.bo.ChatTPropertieBO;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */

@LocalBean
@Stateless(name="ChatPropertieService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ChatPropertieService extends TEjbService<TPropertie> {

	@Inject
	private ChatTPropertieBO bo;
	
	@Override
	public ITGenericBO<TPropertie> getBussinesObject() {
		return bo;
	}

	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void buildProperties() throws Exception {
		bo.buildProperties();
	}

}
