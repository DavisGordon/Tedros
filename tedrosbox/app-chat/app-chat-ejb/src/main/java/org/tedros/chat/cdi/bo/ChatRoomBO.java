/**
 * 
 */
package org.tedros.chat.cdi.bo;

import java.util.List;

import jakarta.inject.Inject;

import org.tedros.chat.cdi.eao.ChatRoomEao;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatUser;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
public class ChatRoomBO extends TGenericBO<Chat> {

	@Inject
	private ChatRoomEao eao;
	
	@Override
	public ITGenericEAO<Chat> getEao() {
		return eao;
	}


	public List<Chat> listAll(ChatUser c){
		return eao.listAll(c.getId());
	}
	

}
