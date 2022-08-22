package org.tedros.core.context;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TCoreKeys;
import org.tedros.core.TLanguage;
import org.tedros.core.TModule;

/**
 * The module descriptor
 * 
 * @author Davis Gordon
 * */
public final class TModuleDescriptor implements Comparable<TModuleDescriptor> {

	private String applicationName;
	private String applicationUUID;
	private String menu;
	private String moduleName;
	private String description;
	
	@Transient
	private Class<? extends TModule> type;
	private String icon;
	private String menuIcon;
	private final TSecurityDescriptor securityDescriptor;
	private TLanguage iEngine;
	
	public TModuleDescriptor(String applicationName, String universalUniqueIdentifier, String menu,  String moduleName, String description, 
			Class<? extends TModule> type, String icon, String menuIcon, 
			TSecurityDescriptor securityDescriptor) {
		
		iEngine = TLanguage.getInstance(applicationUUID);
		
		if(StringUtils.isBlank(applicationName) 
				|| StringUtils.isBlank(menu)
				|| StringUtils.isBlank(moduleName)
				|| type == null)
			throw new IllegalArgumentException("TModuleDescriptor constructor cannot receive null or empty argument.");
		
		this.applicationName = applicationName;
		this.menu = menu;
		this.moduleName = moduleName;
		this.description = description;
		this.type = type;
		this.icon = icon;
		this.menuIcon = menuIcon;
		this.securityDescriptor = securityDescriptor;
	}
	
	
	public final String getApplicationName() {
		return iEngine.getString(applicationName);
	}
	
	public final String getMenu() {
		return iEngine.getString(menu);
	}
	
	public final String getModuleName() {
		return iEngine.getString(moduleName);
	}
	
	public final Class<? extends TModule> getType() {
		return type;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public String getMenuIcon() {
		return menuIcon;
	}
	
	public TSecurityDescriptor getSecurityDescriptor() {
		return securityDescriptor;
	}
		
	@Override
	public int compareTo(TModuleDescriptor o) {
		String thisStr = getApplicationName() + " " + getMenu() + " " + getModuleName();
		String objStr = o.getApplicationName() + " " + o.getMenu() + " " + o.getModuleName();
		return thisStr.compareTo(objStr);
	}

	public String getApplicationUUID() {
		return applicationUUID;
	}

	public void setApplicationUUID(String applicationUUID) {
		this.applicationUUID = applicationUUID;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	public String getPathKeys() {
		return TCoreKeys.MENU_ROOT + "/"+ this.applicationName +"/"+ this.menu +"/"+ this.moduleName;
	}
	
	public String getPath() {
		return TLanguage.getInstance().getString(getPathKeys());
	}
	
}
