/**
 * 
 */
package org.tedros.chat.cdi.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.chat.entity.Chat;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ChatRoomEao extends TGenericEAO<Chat> {

	@SuppressWarnings("unchecked")
	public List<Chat> listAll(Long chatUserId){
		
		String hql = "select distinct e from Chat e "
				+ "join e.participants p "
				+ "join e.owner o "
				+ "where o.id = :id0 or p.id = :id1 "
				+ "order by e.title";
		Query qry = super.getEntityManager().createQuery(hql);
		qry.setParameter("id0", chatUserId);
		qry.setParameter("id1", chatUserId);
		
		return qry.getResultList();
	}
	
}
