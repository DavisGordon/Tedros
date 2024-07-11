/**
 * 
 */
package org.tedros.ai.function.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@JsonClassDescription("the path to call a view")
public class ViewPath {
	
	@JsonPropertyDescription("The view path use it to open a view in the system. Don't change it!")
	private String viewPath;
	
	/**
	 * @param viewPath
	 */
	public ViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	/**
	 * 
	 */
	public ViewPath() {
		super();
	}
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
