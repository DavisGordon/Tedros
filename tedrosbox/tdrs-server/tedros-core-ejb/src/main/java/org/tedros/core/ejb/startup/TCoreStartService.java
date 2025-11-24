package org.tedros.core.ejb.startup;

import org.tedros.core.ejb.service.TPropertieService;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

/**
 * Session Bean implementation class TCoreStartService
 */
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TCoreStartService {

	@EJB
	private TPropertieService serv;
	
	@PostConstruct
	public void init() {
		try {
			serv.buildProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	 

}
