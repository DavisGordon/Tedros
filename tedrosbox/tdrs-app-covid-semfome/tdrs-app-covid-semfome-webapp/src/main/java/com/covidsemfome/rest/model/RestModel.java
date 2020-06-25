/**
 * 
 */
package com.covidsemfome.rest.model;

/**
 * @author Davis Gordon
 *
 */
public class RestModel<T> {
	
	private T data;
	
	private String code;
	
	private String message;
	
	public RestModel(){
		
	}
	
	public RestModel(T data, String code, String message){
		this.data = data;
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
