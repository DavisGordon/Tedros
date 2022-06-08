/**
 * 
 */
package com.tedros.core.ejb.eao;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.tedros.core.notify.model.TNotify;
import com.tedros.core.notify.model.TState;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TNotifyEao extends TGenericEAO<TNotify> {
	
	@SuppressWarnings("unchecked")
	public List<TNotify> listToProcess(){
		StringBuilder sb = new StringBuilder();
		sb.append("select e from TNotify e where e.state in :state");
		
		Query qry = getEntityManager().createQuery(sb.toString());
		
		qry.setParameter("state", Arrays.asList(TState.RESEND, TState.WAITING));
		
		return qry.getResultList();
	}
	
}
