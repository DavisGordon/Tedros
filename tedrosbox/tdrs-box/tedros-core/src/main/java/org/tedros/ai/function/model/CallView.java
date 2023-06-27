/**
 * 
 */
package org.tedros.ai.function.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
public class CallView {

	@JsonPropertyDescription("The view path to open")
	private String viewPath;

	/**
	 * @return the viewPath
	 */
	public String getViewPath() {
		return viewPath;
	}

	/**
	 * @param viewPath the viewPath to set
	 */
	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}
	
	


}
