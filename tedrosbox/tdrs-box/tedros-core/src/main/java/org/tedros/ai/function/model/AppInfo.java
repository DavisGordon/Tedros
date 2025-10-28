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
@JsonClassDescription("Full information about the application")
public class AppInfo {
	
	@JsonPropertyDescription("Application name")
	private String name;
	
	@JsonPropertyDescription("Shows whether the user has access to the application")
	private String isUserWithAccessToTheApp;
	
	@JsonPropertyDescription("List of all modules")
	private List<ModuleInfo> modules;
	
	public AppInfo(String appName, String isUserWithAccessToTheApp, List<ModuleInfo> modules) {
		this.name = appName;
		this.isUserWithAccessToTheApp = isUserWithAccessToTheApp;
		this.modules = modules;
	}
	
	public void addModule(String moduleName, String isUserWithAccessToTheModule, List<ViewInfo> views) {
		this.modules.add(new ModuleInfo(moduleName, isUserWithAccessToTheModule, views));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsUserWithAccessToTheApp() {
		return isUserWithAccessToTheApp;
	}

	public void setIsUserWithAccessToTheApp(String isUserWithAccessToTheApp) {
		this.isUserWithAccessToTheApp = isUserWithAccessToTheApp;
	}

	public List<ModuleInfo> getModules() {
		return modules;
	}

	public void setModules(List<ModuleInfo> modules) {
		this.modules = modules;
	}

}
