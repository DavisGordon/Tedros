/**
 * 
 */
package org.tedros.chat.cdi.eao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

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
				//+ (status!=null ? "join e."+status.name().toLowerCase()+" x " : "")
				+ "where c.id = :id ";
		if(status!=null) {
			if(status.equals(TStatus.SENT))
				hql += "and e.from.id = :id0";
			else {
				hql += "and e.from.id != :id1 ";
				hql += "and exists (select 1 from e."+status.name().toLowerCase()
						+" x where x.id = :id0) ";
			}
		}
		
		Query qry = super.getEntityManager().createQuery(hql);
		qry.setParameter("id", chatId);
		if(status!=null) {
			qry.setParameter("id0", userId);
			if(!status.equals(TStatus.SENT))
				qry.setParameter("id1", userId);
		}
		return (Long) qry.getSingleResult();
	}
	
}
