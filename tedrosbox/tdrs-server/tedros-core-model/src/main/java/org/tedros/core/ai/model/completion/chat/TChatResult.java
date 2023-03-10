/**
 * 
 */
package org.tedros.core.ai.model.completion.chat;

import java.util.ArrayList;
import java.util.List;

import org.tedros.core.ai.model.TBaseCompletionResult;
import org.tedros.core.ai.model.TUsage;

/**
 * @author Davis Gordon
 *
 */
public class TChatResult extends TBaseCompletionResult {

	private static final long serialVersionUID = -1767540673732988570L;

    /**
     * A list of all generated completions.
     */
    private List<TChatChoice> choices;

	/**
	 * 
	 */
	public TChatResult() {
	}

	/**
	 * @param log
	 * @param success
	 */
	public TChatResult(String log, boolean success) {
		super(log, success);
	}

	/**
	 * @param id
	 * @param model
	 * @param usage
	 */
	public TChatResult(String id, String model, TUsage usage) {
		super(id, model, usage);
		super.setSuccess(true);
	}


	public void addChoice(TChatChoice c) {
		if(this.choices==null)
			this.choices = new ArrayList<>();
		this.choices.add(c);
	}

	/**
	 * @return the choices
	 */
	public List<TChatChoice> getChoices() {
		return choices;
	}

	/**
	 * @param choices the choices to set
	 */
	public void setChoices(List<TChatChoice> choices) {
		this.choices = choices;
	}
}
