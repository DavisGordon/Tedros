/**
 * 
 */
package org.tedros.core.ai.model.completion;

import org.tedros.core.ai.model.TAiModel;
import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TCompletionRequest extends TBaseCompletionRequest implements ITModel {

	private static final long serialVersionUID = -8226359450676730401L;

	/**
	 * The name of the model to use.
	 * Required if specifying a fine tuned model or if using the new v1/completions endpoint.
	 */
	protected TAiModel model;
	
	/**
     * Echo back the prompt in addition to the completion
     */
    private Boolean echo = false;

    /**
	 * 
	 */
	public TCompletionRequest() {
	}

	/**
	 * @param prompt
	 * @param user
	 * @param model
	 * @param maxTokens
	 * @param temperature
	 */
	public TCompletionRequest(String prompt, String user, TAiModel model, Integer maxTokens, Double temperature) {
		super(prompt, user, maxTokens, temperature);
		this.model = model;
	}

	/**
	 * @return the echo
	 */
	public Boolean getEcho() {
		return echo;
	}

	/**
	 * @param echo the echo to set
	 */
	public void setEcho(Boolean echo) {
		this.echo = echo;
	}

	/**
	 * @return the model
	 */
	public TAiModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(TAiModel model) {
		this.model = model;
	}


}
