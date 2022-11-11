/**
 * 
 */
package org.tedros.chat.ejb.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

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
