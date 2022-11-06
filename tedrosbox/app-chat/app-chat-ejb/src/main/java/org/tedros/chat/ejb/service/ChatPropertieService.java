/**
 * 
 */
package org.tedros.chat.ejb.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.chat.cdi.bo.CHATBO;
import org.tedros.chat.domain.ChatPropertie;
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

	private CHATBO<TPropertie> bo;
	
	@Override
	public ITGenericBO<TPropertie> getBussinesObject() {
		return bo;
	}
	

	public void buildProperties() throws Exception {
		for(ChatPropertie p : ChatPropertie.values()) {
			TPropertie e = new TPropertie();
			e.setKey(p.getValue());
			if(bo.find(e)==null) {
				e.setName(p.name());
				e.setKey(p.getValue());
				bo.save(e);
			}
		}
	}

}
