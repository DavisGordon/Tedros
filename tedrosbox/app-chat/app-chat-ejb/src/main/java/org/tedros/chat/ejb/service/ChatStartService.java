/**
 * 
 */
package org.tedros.chat.ejb.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Davis Gordon
 *
 */
@Startup
@ApplicationScoped
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ChatStartService {
	

	@EJB
	private ChatPropertieService serv;

	@PostConstruct
	public void init() {
		try {
			serv.buildProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
