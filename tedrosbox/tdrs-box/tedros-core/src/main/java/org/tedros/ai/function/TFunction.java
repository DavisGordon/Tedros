/**
 * 
 */
package org.tedros.ai.function;

import java.util.function.Function;

/**
 * @author Davis Gordon
 *
 */
public class TFunction<T> {
	public static final String SUSCESS_MESSAGE = "Function executed successfully! ";
	public static final String DO_NOT_CALL_AGAIN = "DO NOT call this function again for this content. ";
	public static final String PROCEED_WITH_TEXT_RESPONSE = "Proceed with text response or stop. "; 
	public static final String PROCEED_WITH_HTML_RESPONSE = "Proceed by displaying your response in HTML; to do this, call the corresponding function. ";
	
	private String name;
	private String description;
	private Function<T, Object> callback;
	private Class<T> model;
    private boolean revertToTheAIModelInCaseOfSuccess = true;
	
	/**
	 * @param name
	 * @param description
	 * @param model
	 * @param callback
	 */
	public TFunction(String name, String description, Class<T> model, Function<T, Object> callback) {
		super();
		this.name = name;
		this.description = description;
		this.model = model;
		this.callback = callback;
	}
	
	public void setRevertToTheAIModelInCaseOfSuccess(boolean revertToTheAIModelInCaseOfSuccess) {
		this.revertToTheAIModelInCaseOfSuccess = revertToTheAIModelInCaseOfSuccess;
	}
	
	public boolean itShouldRevertToTheAIModelInCaseOfSuccess() {
		return revertToTheAIModelInCaseOfSuccess;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the callback
	 */
	public Function<T, Object> getCallback() {
		return callback;
	}

	/**
	 * @param callback the callback to set
	 */
	public void setCallback(Function<T, Object> callback) {
		this.callback = callback;
	}

	/**
	 * @return the model
	 */
	public Class<T> getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Class<T> model) {
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TFunction [" + (name != null ? "name=" + name : "") + "]";
	}

}
