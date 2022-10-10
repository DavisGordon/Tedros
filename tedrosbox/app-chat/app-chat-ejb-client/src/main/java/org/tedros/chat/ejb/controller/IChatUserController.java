/**
 * 
 */
package org.tedros.chat.ejb.controller;

import javax.ejb.Remote;

import org.tedros.chat.entity.ChatUser;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IChatUserController extends ITSecureEjbController<ChatUser> {

	static final String JNDI_NAME = "IChatUserControllerRemote";
		
}
