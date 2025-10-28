/**
 * 
 */
package org.tedros.ai.function.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private final List<AppInfo> applications;

	/**
	 * @param systemViewCatalog
	 */
	public AppCatalog() {
		super();
		this.applications = Collections.synchronizedList(new ArrayList<>());
	}

	public void add(String appName, String isUserWithAccessToTheApp, List<ModuleInfo> modules) {
		synchronized (applications) {
			this.applications.add(new AppInfo(appName, isUserWithAccessToTheApp, modules));
		}
	}

	public List<AppInfo> getApplications() {
		synchronized (applications) {
			return Collections.unmodifiableList(new ArrayList<>(applications));
		}
	}

	public void setApplications(List<AppInfo> applications) {
		synchronized (this.applications) {
			this.applications.clear();
			if (applications != null) {
				this.applications.addAll(applications);
			}
		}
	}

}