/**
 * 
 */
package com.tedros.core;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;

/**
 * @author Davis Gordon
 *
 */
public class TSecurityDescriptor {
	
	private final String id;
	
	private final String appName;
	
	private final String moduleName;
	
	private final String viewName;
	
	private final String description;
	
	private final TAuthorizationType[] allowedAccesses;
	
	public TSecurityDescriptor(TSecurity tSecurity) {
		this.id = tSecurity.id();
		this.appName = tSecurity.appName();
		this.moduleName = tSecurity.moduleName();
		this.viewName = tSecurity.viewName();
		this.description = tSecurity.description();
		this.allowedAccesses = tSecurity.allowedAccesses();
	}

	public String getId() {
		return id;
	}

	public String getAppName() {
		return appName;
	}


	public String getModuleName() {
		return moduleName;
	}

	public String getViewName() {
		return viewName;
	}

	public String getDescription() {
		return description;
	}

	public TAuthorizationType[] getAllowedAccesses() {
		return allowedAccesses;
	}	
}
