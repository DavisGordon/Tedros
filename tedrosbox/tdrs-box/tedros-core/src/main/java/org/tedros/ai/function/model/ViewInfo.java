/**
 * 
 */
package org.tedros.ai.function.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
public class ViewInfo {
	
	@JsonPropertyDescription("The application name. An application can have one or more modules")
	private String appName;
	
	@JsonPropertyDescription("The module name. A module can have one or more views")
	private String moduleName;

	@JsonPropertyDescription("The view name.")
	private String viewName;
	
	@JsonPropertyDescription("The view description")
	private String viewDescription;
	
	@JsonPropertyDescription("The system view path, used to open a view in the Tedros system")
	private String viewPath;
	
	@JsonPropertyDescription("Shows whether the user has access to the application")
	private String isUserWithAccessToTheApp;
	
	@JsonPropertyDescription("Shows whether the user has access to the module")
	private String isUserWithAccessToTheModule;
	
	@JsonPropertyDescription("Shows whether the user has access to the view")
	private String isUserWithAccessToTheView;
	
	/**
	 * @param appName
	 * @param moduleName
	 * @param viewName
	 * @param viewDescription
	 * @param viewPath
	 * @param isUserWithAccessToTheApp
	 * @param isUserWithAccessToTheModule
	 * @param isUserWithAccessToTheView
	 */
	public ViewInfo(String appName, String moduleName, String viewName, String viewDescription, String viewPath,
			String isUserWithAccessToTheApp, String isUserWithAccessToTheModule, String isUserWithAccessToTheView) {
		super();
		this.appName = appName;
		this.moduleName = moduleName;
		this.viewName = viewName;
		this.viewDescription = viewDescription;
		this.viewPath = viewPath;
		this.isUserWithAccessToTheApp = isUserWithAccessToTheApp;
		this.isUserWithAccessToTheModule = isUserWithAccessToTheModule;
		this.isUserWithAccessToTheView = isUserWithAccessToTheView;
	}

	/**
	 * 
	 */
	public ViewInfo() {
		super();
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * @return the viewDescription
	 */
	public String getViewDescription() {
		return viewDescription;
	}

	/**
	 * @param viewDescription the viewDescription to set
	 */
	public void setViewDescription(String viewDescription) {
		this.viewDescription = viewDescription;
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

	/**
	 * @return the isUserWithAccessToTheApp
	 */
	public String getIsUserWithAccessToTheApp() {
		return isUserWithAccessToTheApp;
	}

	/**
	 * @param isUserWithAccessToTheApp the isUserWithAccessToTheApp to set
	 */
	public void setIsUserWithAccessToTheApp(String isUserWithAccessToTheApp) {
		this.isUserWithAccessToTheApp = isUserWithAccessToTheApp;
	}

	/**
	 * @return the isUserWithAccessToTheModule
	 */
	public String getIsUserWithAccessToTheModule() {
		return isUserWithAccessToTheModule;
	}

	/**
	 * @param isUserWithAccessToTheModule the isUserWithAccessToTheModule to set
	 */
	public void setIsUserWithAccessToTheModule(String isUserWithAccessToTheModule) {
		this.isUserWithAccessToTheModule = isUserWithAccessToTheModule;
	}

	/**
	 * @return the isUserWithAccessToTheView
	 */
	public String getIsUserWithAccessToTheView() {
		return isUserWithAccessToTheView;
	}

	/**
	 * @param isUserWithAccessToTheView the isUserWithAccessToTheView to set
	 */
	public void setIsUserWithAccessToTheView(String isUserWithAccessToTheView) {
		this.isUserWithAccessToTheView = isUserWithAccessToTheView;
	}
}
