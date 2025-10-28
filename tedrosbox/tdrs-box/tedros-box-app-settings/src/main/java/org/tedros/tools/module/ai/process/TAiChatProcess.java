/**
 * 
 */
package org.tedros.tools.module.ai.process;

import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.controller.TAiChatCompletionController;
import org.tedros.fx.process.TEntityProcess;

/**
 * @author Davis Gordon
 *
 */
public class TAiChatProcess extends TEntityProcess<TAiChatCompletion> {
	
	public TAiChatProcess() {
		super(TAiChatCompletion.class, TAiChatCompletionController.JNDI_NAME);
	}
	
}
