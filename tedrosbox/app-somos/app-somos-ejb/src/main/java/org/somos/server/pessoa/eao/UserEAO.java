/**
 * 
 */
package org.somos.server.pessoa.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.somos.model.Pessoa;
import org.somos.model.User;

import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class UserEAO extends TGenericEAO<User> {

	public User recuperar(String key){
		StringBuffer sbf = new StringBuffer("select distinct e from User e where 1=1 ");
		
		if(StringUtils.isNotBlank(key))
			sbf.append("and e.key = :key ");
		
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(key))
			qry.setParameter("key", key);
		
		return (User) qry.getSingleResult();
	}
	
	public List<User> recuperar(Pessoa pessoa){
		StringBuffer sbf = new StringBuffer("select distinct e from User e join e.pessoa p where p.id = :id ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", pessoa.getId());
		
		return qry.getResultList();
	}
	
	public void remover(Pessoa pessoa){
		StringBuffer sbf = new StringBuffer("delete from User e where e.pessoa.id=:id ");
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		qry.setParameter("id", pessoa.getId());
		
		qry.executeUpdate();
	}
}
