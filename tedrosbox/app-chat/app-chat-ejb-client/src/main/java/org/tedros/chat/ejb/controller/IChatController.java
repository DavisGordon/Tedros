/**
 * 
 */
package org.tedros.chat.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.chat.entity.Chat;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IChatController extends ITSecureEjbController<Chat> {

	static final String JNDI_NAME = "IChatControllerRemote";
		
}
