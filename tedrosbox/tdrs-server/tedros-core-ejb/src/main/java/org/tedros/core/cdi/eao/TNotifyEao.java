/**
 * 
 */
package org.tedros.core.cdi.eao;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

import org.tedros.core.notify.model.TNotify;
import org.tedros.core.notify.model.TState;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TNotifyEao extends TGenericEAO<TNotify> {
	
	@SuppressWarnings("unchecked")
	public List<TNotify> listToProcess(){
		StringBuilder sb = new StringBuilder();
		sb.append("select e from TNotify e where e.state = :state");
		
		Query qry = getEntityManager().createQuery(sb.toString());
		
		qry.setParameter("state", TState.QUEUED);
		
		return qry.getResultList();
	}
	
}
