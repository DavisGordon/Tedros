/**
 * 
 */
package org.tedros.core.controller;

import javax.ejb.Remote;

import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface TAiChatCompletionController extends ITSecureEjbController<TAiChatCompletion> {

	static final String JNDI_NAME = "TAiChatCompletionControllerRemote";
	
	TResult<TChatResult> chat(TAccessToken token, TChatRequest request);
}
