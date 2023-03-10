/**
 * 
 */
package org.tedros.core.ai.model.completion.chat;

import java.util.ArrayList;
import java.util.List;

import org.tedros.core.ai.model.TAiChatModel;
import org.tedros.core.ai.model.completion.TBaseCompletionRequest;
/**
 * @author Davis Gordon
 *
 */
public class TChatRequest extends TBaseCompletionRequest{

	private static final long serialVersionUID = 5022454326771423029L;

	/**
     * ID of the model to use. Currently, only gpt-3.5-turbo and gpt-3.5-turbo-0301 are supported.
     */
    private TAiChatModel model;

    /**
     * The messages to generate chat completions for, in the <a
     * href="https://platform.openai.com/docs/guides/chat/introduction">chat format</a>.<br>
     * see {@link ChatMessage}
     */
    private List<TChatMessage> messages;
    
	/**
	 * 
	 */
	public TChatRequest() {
	}
	
	
	/**
	 * @param prompt
	 * @param user
	 * @param maxTokens
	 * @param temperature
	 * @param model
	 */
	public TChatRequest(String prompt, String user, Integer maxTokens, Double temperature, TAiChatModel model) {
		super(prompt, user, maxTokens, temperature);
		this.model = model;
	}


	public void addMessage(TChatMessage m) {
		if(this.messages==null)
			this.messages = new ArrayList<>();
		this.messages.add(m);
	}


	/**
	 * @return the model
	 */
	public TAiChatModel getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(TAiChatModel model) {
		this.model = model;
	}


	/**
	 * @return the messages
	 */
	public List<TChatMessage> getMessages() {
		return messages;
	}


	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<TChatMessage> messages) {
		this.messages = messages;
	}
	

}
