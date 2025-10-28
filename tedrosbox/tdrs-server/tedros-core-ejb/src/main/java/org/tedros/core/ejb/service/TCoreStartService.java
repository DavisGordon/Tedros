package org.tedros.core.ejb.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Session Bean implementation class TCoreStartService
 */
@Startup
@ApplicationScoped
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
