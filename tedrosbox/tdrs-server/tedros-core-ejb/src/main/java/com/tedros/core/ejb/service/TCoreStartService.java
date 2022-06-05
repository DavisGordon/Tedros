package com.tedros.core.ejb.service;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;

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
