/**
 * 
 */
package org.tedros.chat.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.chat.entity.ChatSetting;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IChatSettingController extends ITSecureEjbController<ChatSetting> {

	static final String JNDI_NAME = "IChatSettingControllerRemote";
		
}
