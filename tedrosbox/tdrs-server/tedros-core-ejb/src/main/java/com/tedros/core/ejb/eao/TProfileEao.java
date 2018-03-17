/**
 * 
 */
package com.tedros.core.ejb.eao;

import javax.persistence.EntityManager;

import com.tedros.core.security.model.TAuthorization;
import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
public class TProfileEao extends TGenericEAO<TProfile> {
	
	@Override
	public void beforePersist(EntityManager em, TProfile entity) throws Exception {
		setProfileAuthorization(entity);
	}

	public void setProfileAuthorization(TProfile entity) {
		for(TAuthorization e : entity.getAutorizations()){
			e.setProfile(entity);
		}
	}
	
	@Override
	public void beforeMerge(EntityManager em, TProfile entity) throws Exception {
		setProfileAuthorization(entity);
	}

}
