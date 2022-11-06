/**
 * 
 */
package org.tedros.chat.ejb.service;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;

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
