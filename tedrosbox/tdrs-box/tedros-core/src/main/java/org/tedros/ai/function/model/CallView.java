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

	@JsonPropertyDescription("The path to open a view on the Tedros desktop system, do not change it")
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
