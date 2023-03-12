/**
 * 
 */
package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.core.ai.model.TAiChatMessage;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface TAiChatMessageController extends ITSecureEjbController<TAiChatMessage> {

	static final String JNDI_NAME = "TAiChatMessageControllerRemote";
}
