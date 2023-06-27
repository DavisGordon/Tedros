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

	/**
	 * @param message
	 */
	public Response(String message) {
		super();
		this.message = message;
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
	

}
