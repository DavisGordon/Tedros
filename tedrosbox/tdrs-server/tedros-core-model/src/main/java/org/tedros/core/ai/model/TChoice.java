/**
 * 
 */
package org.tedros.core.ai.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TChoice implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1420043998421462412L;

	/**
     * The generated text. Will include the prompt if {@link CompletionRequest#echo } is true
     */
    private String text;

    /**
     * This index of this completion in the returned list.
     */
    private Integer index;

    /**
     * The reason why GPT-3 stopped generating, for example "length".
     */
    private String finish_reason;
	
	/**
	 * 
	 */
	public TChoice() {
	}

	/**
	 * @param text
	 * @param index
	 * @param finish_reason
	 */
	public TChoice(String text, Integer index, String finish_reason) {
		this.text = text;
		this.index = index;
		this.finish_reason = finish_reason;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	 * @return the finish_reason
	 */
	public String getFinish_reason() {
		return finish_reason;
	}

	/**
	 * @param finish_reason the finish_reason to set
	 */
	public void setFinish_reason(String finish_reason) {
		this.finish_reason = finish_reason;
	}

}
