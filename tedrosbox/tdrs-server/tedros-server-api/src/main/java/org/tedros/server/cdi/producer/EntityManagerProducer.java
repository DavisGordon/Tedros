/**
 * 
 */
package org.tedros.server.cdi.producer;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;

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
