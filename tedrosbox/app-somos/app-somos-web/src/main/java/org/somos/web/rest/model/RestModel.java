/**
 * 
 */
package org.somos.web.rest.model;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Davis Gordon
 *
 */
@XmlRootElement 
public class RestModel<T>  extends HashMap<String, Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2277098289635034074L;
	
	public RestModel(T data, String code, String message){
		this.put("data", data);
		this.put("code", code);
		this.put("message", message);
	}

	/**
	 * @return the data
	 */
	@SuppressWarnings("unchecked")
	public T getData() {
		return (T) this.get("data");
	}

	

	/**
	 * @return the code
	 */
	public String getCode() {
		return (String) get("code");
	}

	

	/**
	 * @return the message
	 */
	public String getMessage() {
		return (String) get("message");
	}

	
	
	

}
