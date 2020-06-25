/**
 * 
 */
package com.tedros.ejb.base.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * @author Davis Gordon
 *
 */
public class EntityManagerProducer {

	//@PersistenceContext(unitName = "tedros_core_pu", type=PersistenceContextType.TRANSACTION)
    private EntityManager em;
	
	/*@Produces
	@Named("em")
	@RequestScoped*/
	public  EntityManager getEntityManager(){
		return em;
	}
    
	
}
