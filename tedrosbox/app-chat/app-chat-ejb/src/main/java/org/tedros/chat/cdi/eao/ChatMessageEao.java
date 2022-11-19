/**
 * 
 */
package org.tedros.chat.cdi.eao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.TStatus;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ChatMessageEao extends TGenericEAO<ChatMessage> {
	
	public Long count(Long chatId, Long userId, TStatus status){
		
		String hql = "select count(e) from ChatMessage e "
				+ "join e.chat c "
				+ "join e."+status.name().toLowerCase()+" x "
				+ "where c.id = :id "
				+ "and x.id = :id0";
		
		Query qry = super.getEntityManager().createQuery(hql);
		qry.setParameter("id", chatId);
		qry.setParameter("id0", userId);
		
		return (Long) qry.getSingleResult();
	}
	
}
