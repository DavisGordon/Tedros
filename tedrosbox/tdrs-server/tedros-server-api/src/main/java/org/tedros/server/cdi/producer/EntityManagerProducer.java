/**
 * 
 */
package org.tedros.server.cdi.producer;

import jakarta.persistence.EntityManager;

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
