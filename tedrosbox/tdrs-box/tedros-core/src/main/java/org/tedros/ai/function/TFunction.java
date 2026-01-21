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
	
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String STATUS = "status";
	public static final String ACTION = "action";
	public static final String INFO_MESSAGE = "info_message";
	public static final String ERROR_MESSAGE = "error_message";
	public static final String SYSTEM_INSTRUCTION = "system_instruction";
	
	public static final String NO_DATA_FOUND_MESSAGE = "No data found. ";
	public static final String FILE_DOWNLOADED_SUCCESSFULLY_DO_NOT_RETRY = "File downloaded successfully. Do not retry.";
	public static final String CONTENT_LOADED_IN_VIEW_FOR_USER_REVIEW_DO_NOT_RETRY = "Content loaded in view for user review. Do not retry.";
	
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
