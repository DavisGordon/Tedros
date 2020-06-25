/**
 * 
 */
package com.tedros.ejb.base.util;

/**
 * @author Davis Gordon
 *
 */
public class LookupUtil {
	
	 
	public static String createLookUpName(boolean remoteNamingProject, Class clazz, String appName, String module, String version) {
	// Application name of the deployed EJBs
	//final String appName = "remote";
	// EJB Modul name
	final String moduleName = module+"-"+version;
	// not used
	final String distinctName = "";
	// The bean implementation class
	final String beanName = clazz.getSimpleName();
	// the remote view fully qualified class name
	final String viewClassName = clazz.getName();
	 
	StringBuilder nameBuilder = new StringBuilder();
	if (!remoteNamingProject) {
	nameBuilder.append("ejb:"); // ejb: prefix
	}
	 
	nameBuilder.append(appName);
	nameBuilder.append("/");
	nameBuilder.append(moduleName);
	nameBuilder.append("/");
	nameBuilder.append(distinctName);
	nameBuilder.append("/");
	nameBuilder.append(beanName);
	nameBuilder.append("!");
	nameBuilder.append(viewClassName);
	System.out.println("-> nameBuilder = " + nameBuilder.toString());
	return nameBuilder.toString();
	}

}
