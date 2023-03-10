/**
 * 
 */
package org.tedros.core.ai.model.completion;

import java.util.ArrayList;
import java.util.List;

import org.tedros.core.ai.model.TBaseCompletionResult;
import org.tedros.core.ai.model.TUsage;

/**
 * @author Davis Gordon
 *
 */
public class TCompletionResult extends TBaseCompletionResult {

	private static final long serialVersionUID = 2594796641023235785L;
	
    /**
     * A list of generated completions.
     */
    private List<TChoice> choices;

	/**
	 * 
	 */
	public TCompletionResult() {
	}

	/**
	 * @param log
	 * @param success
	 */
	public TCompletionResult(String log, boolean success) {
		super(log, success);
	}

	/**
	 * @param id
	 * @param model
	 * @param usage
	 */
	public TCompletionResult(String id, String model, TUsage usage) {
		super(id, model, usage);
		super.setSuccess(true);
	}

	public void addChoice(String res, Integer index, String reason) {
		if(choices==null)
			choices = new ArrayList<>();
		choices.add(new TChoice(res, index, reason));
	}

	/**
	 * @return the choices
	 */
	public List<TChoice> getChoices() {
		return choices;
	}

	/**
	 * @param choices the choices to set
	 */
	public void setChoices(List<TChoice> choices) {
		this.choices = choices;
	}

}
