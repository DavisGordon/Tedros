/**
 * 
 */
package org.tedros.core.ai.model;


/**
 * @author Davis Gordon
 *
 */
public abstract class TBaseCompletionResult extends TBaseResult {

	private static final long serialVersionUID = -3853789278182003346L;

	/**
     * A unique id assigned to this completion.
     */
    private String id;

    /**
     * The GPT-3 model used.
     */
    private String model;

    /**
     * The API usage for this request
     */
    private TUsage usage;
	
	/**
	 * 
	 */
	public TBaseCompletionResult() {
	}

	/**
	 * @param log
	 * @param success
	 */
	public TBaseCompletionResult(String log, boolean success) {
		super(log, success);
	}

	public TBaseCompletionResult(String log, String errorCode) {
		super(log, errorCode);
	}

	/**
	 * @param id
	 * @param model
	 * @param usage
	 */
	public TBaseCompletionResult(String id, String model, TUsage usage) {
		this.id = id;
		this.model = model;
		this.usage = usage;
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
	 * @return the usage
	 */
	public TUsage getUsage() {
		return usage;
	}

	/**
	 * @param usage the usage to set
	 */
	public void setUsage(TUsage usage) {
		this.usage = usage;
	}

}
