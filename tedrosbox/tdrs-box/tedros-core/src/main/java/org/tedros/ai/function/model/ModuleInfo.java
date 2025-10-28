/**
 * 
 */
package org.tedros.ai.function.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@JsonClassDescription("Full information about the module")
public class ModuleInfo {
	
	@JsonPropertyDescription("Module name")
	private String name;
	
	@JsonPropertyDescription("Shows whether the user has access to the module")
	private String isUserWithAccessToTheModule;
	
	@JsonPropertyDescription("List of all views")
	private List<ViewInfo> views;
	
	public ModuleInfo(String moduleName, String isUserWithAccessToTheModule, List<ViewInfo> views) {
		this.name = moduleName;
		this.isUserWithAccessToTheModule = isUserWithAccessToTheModule;
		this.views = views;
	}
	
	public void addView(String viewName, String viewDescription, String viewPath, String isUserWithAccessToTheView) {
		this.views.add(new ViewInfo(viewName, viewDescription, viewPath, isUserWithAccessToTheView));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsUserWithAccessToTheModule() {
		return isUserWithAccessToTheModule;
	}

	public void setIsUserWithAccessToTheModule(String isUserWithAccessToTheModule) {
		this.isUserWithAccessToTheModule = isUserWithAccessToTheModule;
	}

	public List<ViewInfo> getViews() {
		return views;
	}

	public void setViews(List<ViewInfo> views) {
		this.views = views;
	}

}
