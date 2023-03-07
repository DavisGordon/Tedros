/**
 * 
 */
package org.tedros.core.ai.model;

import java.util.ArrayList;
import java.util.List;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TCompletionResult implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2594796641023235785L;

	/**
     * A unique id assigned to this completion.
     */
    private String id;

    /**
     * The creation time in epoch seconds.
     */
    private long created;

    /**
     * The GPT-3 model used.
     */
    private String model;

    /**
     * A list of generated completions.
     */
    private List<TChoice> choices;
    
    private String log;
    
    private boolean success;
	
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 
	 */
	public TCompletionResult() {
	}

	/**
	 * @param id
	 * @param created
	 * @param model
	 */
	public TCompletionResult(String id, long created, String model) {
		this.success = true;
		this.id = id;
		this.created = created;
		this.model = model;
	}
	
	/**
	 * @param log
	 * @param success
	 */
	public TCompletionResult(String log, boolean success) {
		this.log = log;
		this.success = success;
	}

	public void addChoice(String res, Integer index, String reason) {
		if(choices==null)
			choices = new ArrayList<>();
		choices.add(new TChoice(res, index, reason));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the created
	 */
	public long getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(long created) {
		this.created = created;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
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

	/**
	 * @return the log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(String log) {
		this.log = log;
	}

}
