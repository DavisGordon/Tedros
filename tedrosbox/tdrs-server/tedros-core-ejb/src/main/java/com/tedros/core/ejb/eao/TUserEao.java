/**
 * 
 */
package com.tedros.core.ejb.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TUserEao extends TGenericEAO<TUser> {
	
	public void saveActiveProfile(TProfile profile, Long userId) throws Exception{
		TUser user = new TUser();
		user.setId(userId);
		user = this.find(user);
		user.setActiveProfile(profile);
		persist(user);
	}
	
	public TUser getUserByLoginPassword(String login, String password){
		Query qry = getEntityManager().createQuery("select e from TUser e where e.login = :login and e.password = :password and e.active = :active");
		qry.setParameter("login", login);
		qry.setParameter("password", password);
		qry.setParameter("active", "T");
		TUser user;
		try{
			user = (TUser) qry.getSingleResult();
		}catch(NoResultException e){
			user = null;
		}
		return user;
	}
	
	/*@Override
	public List<TUser> listAll(EntityManager em, Class<? extends ITEntity> entidade) throws Exception {
		List<TUser> lst = super.listAll(em, entidade);
		if(lst!=null)
			for (TUser tUser : lst) {
				em.detach(tUser);
				tUser.setPassword(null);
			}
		return lst;
	}*/

}
