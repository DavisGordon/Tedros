/**
 * 
 */
package com.tedros.core.ejb.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TOwnerEao extends TGenericEAO<TOwner> {
	
	
	public TOwner getOwner(){
		Query qry = getEntityManager().createQuery("select e from TOwner e left join e.logo l left join l.byteEntity b");
		TOwner Owner;
		try{
			Owner = (TOwner) qry.getSingleResult();
		}catch(NoResultException e){
			Owner = null;
		}
		return Owner;
	}
	
}
