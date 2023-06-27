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

	private String name;
	private String description;
	private Function<T, Object> callback;
	private Class<T> model;
	
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

	/**
	 * 
	 */
	public TFunction() {
		// TODO Auto-generated constructor stub
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

}
