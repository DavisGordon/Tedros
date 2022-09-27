/**
 * 
 */
package org.tedros.chat.cdi.eao;

import javax.enterprise.context.RequestScoped;

import org.tedros.chat.entity.ChatMessage;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TMessageEao extends TGenericEAO<ChatMessage> {
	

}
