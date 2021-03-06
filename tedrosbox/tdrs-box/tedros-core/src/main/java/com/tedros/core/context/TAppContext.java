/**
 * 
 */
package com.tedros.core.context;

import java.util.List;
import java.util.logging.Logger;

import com.tedros.app.process.ITProcess;
import com.tedros.core.ITApplication;
import com.tedros.core.ITModule;
import com.tedros.core.TAppDescriptor;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.TModuleDescriptor;
import com.tedros.core.annotation.security.TAuthorizationType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The application context.
 * 
 * @author davis.dun
 */
public final class TAppContext {
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private TInternationalizationEngine iEngine;
	
	private final TAppDescriptor appDescriptor;
	
	private ObservableList<TModuleContext> moduleContextList;
	
	@SuppressWarnings("rawtypes")
	public TAppContext(Class appStarterClass) {
		
		moduleContextList = FXCollections.observableArrayList(); 
		
		appDescriptor = new TAppDescriptor(appStarterClass);
				
		try {
			
			LOGGER.info("Start load app class: "+appStarterClass.getName());
			
			if(appDescriptor.getResourceNameList()!=null){
				LOGGER.info("Start loading app resources to the Internationalization Engine:");
				for(String bundle : appDescriptor.getResourceNameList()){
					TInternationalizationEngine.addResourceBundle(appDescriptor.getUniversalUniqueIdentifier(), bundle, appStarterClass.getClassLoader());
					LOGGER.info(bundle + " added.");
				}
				LOGGER.info("Finish loading app resources to the Internationalization Engine.");
			}
			
			iEngine = TInternationalizationEngine.getInstance(appDescriptor.getUniversalUniqueIdentifier());
								
			if(appDescriptor.getSecurityDescriptor()!=null){
				if(!TedrosContext.isUserAuthorized(appDescriptor.getSecurityDescriptor(), TAuthorizationType.APP_ACCESS)){
					LOGGER.info("App access disabled by security policy.");
					return;
				}
			}
			
			if(TedrosCoreUtil.isImplemented(appStarterClass, ITApplication.class)){
				LOGGER.info("Calling start method:");
				ITApplication appStarterInstance = (ITApplication) appStarterClass.newInstance();
				appStarterInstance.start();
				LOGGER.info("Finish start method.");
			}
			
			LOGGER.info("Start process manager");
			for(Class<? extends ITProcess> c : appDescriptor.getProcessList()){
				TedrosProcessManager.addProcess(c);
			}	
			
			LOGGER.info("Start load app modules:");
			for(TModuleDescriptor tModuleDescriptor: appDescriptor.getModulesDescriptor()){
				moduleContextList.add(new TModuleContext(this, tModuleDescriptor));
				LOGGER.info("Module "+iEngine.getString(tModuleDescriptor.getModuleName())+" loaded.");
			}
			
		}catch(Exception e){
			TedrosContext.updateInitializationErrorMessage(e.getMessage());
			LOGGER.severe(e.toString());
		}
	}
	
	public TModuleContext findModuleContext(ITModule module){
		for (TModuleContext tModuleContext : moduleContextList) {
			if(tModuleContext.getModule()!=null && tModuleContext.getModule().equals(module)){
				return tModuleContext;
			}
		}
		
		return null;
	}
	
	public boolean isModuleContextPresent(ITModule module){
		for (TModuleContext tModuleContext : moduleContextList) {
			if(tModuleContext.getModule().equals(module)){
				return true;
			}	
		}
		return false;
	}
	
	public void removeModuleContext(ITModule module){
		TModuleContext moduleContext = findModuleContext(module);
		if(moduleContext!=null){
			moduleContextList.remove(moduleContext);
			moduleContext = null;
		}
	}
	
	public ObservableList<TModuleContext> getModuleContextProperty() {
		return moduleContextList;
	}
	
	public List<TModuleContext> getModulesContext() {
		return moduleContextList;
	}
	
	public TAppDescriptor getAppDescriptor() {
		return appDescriptor;
	}

}
