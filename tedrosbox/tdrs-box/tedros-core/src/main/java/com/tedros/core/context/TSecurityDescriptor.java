/**
 * 
 */
package com.tedros.core.context;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;

/**
 * The security descriptor
 * 
 * @author Davis Gordon
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
	
	/**
	 * Returns the security unique identification.
	 * */
	public String getId() {
		return id;
	}

	/**
	 * Returns the app name
	 * */
	public String getAppName() {
		return appName;
	}

	/**
	 * Returns the module name 
	 * */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Returns the view name
	 * */
	public String getViewName() {
		return viewName;
	}

	/**
	 * Returns the description for this security definition
	 * */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the allowed accesses for this security definition
	 * */
	public TAuthorizationType[] getAllowedAccesses() {
		return allowedAccesses;
	}	
}
