package org.tedros.core.ai.model.completion.chat;

import org.tedros.server.model.ITModel;

public class TChatChoice implements ITModel {

	private static final long serialVersionUID = -7085874062779766080L;

	/**
     * This index of this completion in the returned list.
     */
    private Integer index;

    /**
     * The {@link ChatMessageRole#assistant} message which was generated.
     */
    private TChatMessage message;

    /**
     * The reason why GPT-3 stopped generating, for example "length".
     */
    private String finishReason;
	
	public TChatChoice() {
	}

	/**
	 * @param index
	 * @param message
	 * @param finishReason
	 */
	public TChatChoice(Integer index, TChatMessage message, String finishReason) {
		this.index = index;
		this.message = message;
		this.finishReason = finishReason;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the message
	 */
	public TChatMessage getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(TChatMessage message) {
		this.message = message;
	}

	/**
	 * @return the finishReason
	 */
	public String getFinishReason() {
		return finishReason;
	}

	/**
	 * @param finishReason the finishReason to set
	 */
	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}

}
