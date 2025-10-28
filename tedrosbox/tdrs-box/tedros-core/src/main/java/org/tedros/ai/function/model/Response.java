/**
 * 
 */
package org.tedros.ai.function.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
public class Response {

	@JsonPropertyDescription("The system response message")
	private String message;
	
	@JsonPropertyDescription("The response object")
	private Object object;

	/**
	 * @param message
	 */
	public Response(String message) {
		super();
		this.message = message;
	}

	/**
	 * @param message
	 * @param object
	 */
	public Response(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	

}
