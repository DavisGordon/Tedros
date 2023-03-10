/**
 * 
 */
package org.tedros.core.ai.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TUsage implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5580584416068479204L;

	/**
     * The number of prompt tokens used.
     */
    private long promptTokens;

    /**
     * The number of completion tokens used.
     */
    private long completionTokens;

    /**
     * The number of total tokens used
     */
    private long totalTokens;
	
	/**
	 * 
	 */
	public TUsage() {
	}

	/**
	 * @param promptTokens
	 * @param completionTokens
	 * @param totalTokens
	 */
	public TUsage(long promptTokens, long completionTokens, long totalTokens) {
		this.promptTokens = promptTokens;
		this.completionTokens = completionTokens;
		this.totalTokens = totalTokens;
	}

	/**
	 * @return the promptTokens
	 */
	public long getPromptTokens() {
		return promptTokens;
	}

	/**
	 * @param promptTokens the promptTokens to set
	 */
	public void setPromptTokens(long promptTokens) {
		this.promptTokens = promptTokens;
	}

	/**
	 * @return the completionTokens
	 */
	public long getCompletionTokens() {
		return completionTokens;
	}

	/**
	 * @param completionTokens the completionTokens to set
	 */
	public void setCompletionTokens(long completionTokens) {
		this.completionTokens = completionTokens;
	}

	/**
	 * @return the totalTokens
	 */
	public long getTotalTokens() {
		return totalTokens;
	}

	/**
	 * @param totalTokens the totalTokens to set
	 */
	public void setTotalTokens(long totalTokens) {
		this.totalTokens = totalTokens;
	}

}
