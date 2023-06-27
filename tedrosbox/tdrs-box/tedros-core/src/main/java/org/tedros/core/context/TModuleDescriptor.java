package org.tedros.core.context;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TCoreKeys;
import org.tedros.core.TLanguage;
import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TSecurity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
	private ObservableList<TViewDescriptor> viewDescriptors;
	
	@Transient
	private Class<? extends TModule> type;
	private String icon;
	private String menuIcon;
	private final TSecurityDescriptor securityDescriptor;
	private TLanguage iEngine;
	
	@SuppressWarnings("rawtypes")
	public TModuleDescriptor(String applicationName, String universalUniqueIdentifier, String menu,  String moduleName, String description, 
			Class<? extends TModule> type, String icon, String menuIcon, 
			TSecurityDescriptor securityDescriptor) {
		
		if(StringUtils.isBlank(applicationName) 
				|| StringUtils.isBlank(menu)
				|| StringUtils.isBlank(moduleName)
				|| type == null)
			throw new IllegalArgumentException("TModuleDescriptor constructor cannot receive null "
					+ "or empty argument.");
		
		iEngine = TLanguage.getInstance(applicationUUID);
		this.viewDescriptors = FXCollections.observableArrayList();
		this.applicationName = applicationName;
		this.menu = menu;
		this.moduleName = moduleName;
		this.description = description;
		this.type = type;
		this.icon = icon;
		this.menuIcon = menuIcon;
		this.securityDescriptor = securityDescriptor;
		
		TView ann = type.getAnnotation(TView.class);
		if(ann!=null) {
			TItem[] arr = ann.items();
			for(TItem i : arr) {
				TSecurity tSecurity = (TSecurity) i.modelView().getAnnotation(TSecurity.class);
				TSecurityDescriptor sdt = (tSecurity!=null) ? new TSecurityDescriptor(tSecurity) : null;
				this.viewDescriptors.add(new TViewDescriptor(this, i.title(), i.description(), i.model(), i.modelView(), sdt));
			
				TEntry e = TEntryBuilder.create()
						.addEntry(TedrosModuleLoader.MODEL, i.model())
						.addEntry(TedrosModuleLoader.MODELVIEW, i.modelView())
						.addEntry(TedrosModuleLoader.MODULE, type)
						.build();
				TedrosModuleLoader.getInstance().entries.add(e);
			
			}
		}
	}
	
	/**
	 * @return the viewDescriptors
	 */
	public ObservableList<TViewDescriptor> getViewDescriptors() {
		return viewDescriptors;
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
