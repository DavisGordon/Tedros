package org.tedros.core.ai.model.completion;

import org.tedros.server.model.ITModel;

public class TBaseRequest implements ITModel {

	private static final long serialVersionUID = -8142282649284227830L;
	/**
	 * An optional prompt to complete from
	 */
	protected String prompt;
	/**
	 * A unique identifier representing your end-user, which will help OpenAI to monitor and detect abuse.
	 */
	protected String user;

	public TBaseRequest() {
		super();
	}

	/**
	 * @param prompt
	 * @param user
	 */
	public TBaseRequest(String prompt, String user) {
		this.prompt = prompt;
		this.user = user;
	}

	/**
	 * @return the prompt
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

}