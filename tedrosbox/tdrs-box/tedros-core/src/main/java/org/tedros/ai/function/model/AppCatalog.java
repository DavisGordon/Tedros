/**
 * 
 */
package org.tedros.ai.function.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@JsonClassDescription("Catalogue of all the system's applications, their modules and views")
public class AppCatalog {

	@JsonPropertyDescription("List of all views in the applications")
	List<AppInfo> applications;

	/**
	 * @param systemViewCatalog
	 */
	public AppCatalog() {
		super();
		this.applications = new ArrayList<>();
	}

	public void add(String appName, String isUserWithAccessToTheApp, List<ModuleInfo> modules) {
		this.applications.add(new AppInfo(appName, isUserWithAccessToTheApp, modules));
	}

	public List<AppInfo> getApplications() {
		return applications;
	}

	public void setApplications(List<AppInfo> applications) {
		this.applications = applications;
	}

}
