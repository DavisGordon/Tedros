/**
 * 
 */
package com.tedros.core.context;

import java.util.Arrays;
import java.util.List;

import com.tedros.app.process.ITProcess;
import com.tedros.core.TLanguage;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.image.TImageView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * App descriptor
 * 
 * @author Davis Gordon
 *
 */
public class TAppDescriptor {
	
	@SuppressWarnings("rawtypes")
	private final Class appStarterClass;

	/**
	 * The application name to show
	 * */
	private final String name;
	
	/**
	 * An universal unique identifier for the application. 
	 * */
	private final String universalUniqueIdentifier;
	
	/***
	 * The application version
	 * */
	private final String version;
	
	/**
	 * The application description
	 * */
	private final String description;
	
	private final ObservableList<String> resourceNameList;
	
	@SuppressWarnings("rawtypes")
	private final ObservableList<Class<? extends ITProcess>> processList;
	
	/**
	 * A list of module of the application
	 * */
	private final ObservableList<TModuleDescriptor> moduleDescriptorList;
	
	private final TSecurityDescriptor securityDescriptor;
	
	private TLanguage iEngine;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TAppDescriptor(Class appClass) 
	{	
		this.appStarterClass = appClass;
		
		TApplication tApplication = (TApplication) appStarterClass.getAnnotation(TApplication.class);
		
		iEngine = TLanguage.getInstance(tApplication.universalUniqueIdentifier());
		
		this.name = tApplication.name();
		this.universalUniqueIdentifier = tApplication.universalUniqueIdentifier();
		this.version = tApplication.version();
		this.description = tApplication.description();
		this.resourceNameList = FXCollections.observableArrayList();
		this.moduleDescriptorList = FXCollections.observableArrayList();
				
		TResourceBundle tResourceBundle = (TResourceBundle) appStarterClass.getAnnotation(TResourceBundle.class);
		if(tResourceBundle!=null)
			for(String bundle : tResourceBundle.resourceName())
				resourceNameList.add(bundle);
		
		processList = (ObservableList<Class<? extends ITProcess>>) ((tApplication.process()!=null) 
				? FXCollections.observableArrayList(Arrays.asList(tApplication.process()))
					: FXCollections.observableArrayList());
		
		TSecurity tSecurity = (TSecurity) appStarterClass.getAnnotation(TSecurity.class);
		this.securityDescriptor = (tSecurity!=null) ? new TSecurityDescriptor(tSecurity) : null;
		
		for(TModule tModule: tApplication.module()){
			TSecurityDescriptor tsd = null;
			TSecurity tModuleSecurity = (TSecurity) tModule.type().getAnnotation(TSecurity.class);
			if(tModuleSecurity!=null)
				tsd = new TSecurityDescriptor(tModuleSecurity);		
			
			final TModuleDescriptor tModuleDescriptor = new TModuleDescriptor(tApplication.name(), universalUniqueIdentifier, 
					tModule.menu(), tModule.name(), tModule.description(), tModule.type(), 
					(tModule.icon()==TImageView.class ? null : tModule.icon()), 
					(tModule.menuIcon()==TImageView.class ? null : tModule.menuIcon()), tsd);
			
			moduleDescriptorList.add(tModuleDescriptor);
		}
	
	}
	
	public TSecurityDescriptor getSecurityDescriptor() {
		return securityDescriptor;
	}
	
	public void addModuleDescriptor(TModuleDescriptor moduleDescriptor){
		this.moduleDescriptorList.add(moduleDescriptor);
	}
	
	public List<TModuleDescriptor> getModulesDescriptor() {
		return moduleDescriptorList;
	}
	
	public ObservableList<TModuleDescriptor> getModulesDescriptorProperty() {
		return moduleDescriptorList;
	}

	public String getName() {
		return iEngine.getString(name);
	}

	public String getUniversalUniqueIdentifier() {
		return universalUniqueIdentifier;
	}

	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return iEngine.getString(description);
	}

	@SuppressWarnings("rawtypes")
	public ObservableList<Class<? extends ITProcess>> getProcessProperty() {
		return processList;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Class<? extends ITProcess>> getProcessList() {
		return processList;
	}
	
	public ObservableList<String> getResourceNameProperty() {
		return resourceNameList;
	}
	
	public List<String> getResourceNameList() {
		return resourceNameList;
	}

	/**
	 * @return the appStarterClass
	 */
	@SuppressWarnings("rawtypes")
	public Class getAppStarterClass() {
		return appStarterClass;
	}
	
	

}
