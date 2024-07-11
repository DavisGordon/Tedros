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
@JsonClassDescription("Full information about the view")
public class ViewInfo extends ViewPath{
	
	@JsonPropertyDescription("View name")
	private String name;
	
	@JsonPropertyDescription("View description")
	private String description;
	
	@JsonPropertyDescription("Shows whether the user has access to the view")
	private String isUserWithAccessToTheView;

	public ViewInfo(String viewPath, String viewName, String viewDescription, String isUserWithAccessToTheView) {
		super(viewPath);
		this.name = viewName;
		this.description = viewDescription;
		this.isUserWithAccessToTheView = isUserWithAccessToTheView;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsUserWithAccessToTheView() {
		return isUserWithAccessToTheView;
	}

	public void setIsUserWithAccessToTheView(String isUserWithAccessToTheView) {
		this.isUserWithAccessToTheView = isUserWithAccessToTheView;
	}

	

}
