/**
 * 
 */
package org.tedros.core.context;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.tedros.app.process.ITProcess;
import org.tedros.core.ITApplication;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.util.TLoggerUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The application context.
 * 
 * @author davis.dun
 */
public final class TAppContext {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(TAppContext.class);
	
	private TLanguage iEngine;
	
	private TAppDescriptor appDescriptor;
	private ITApplication application;
	
	private ObservableList<TModuleContext> moduleContextList;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TAppContext(Class appStarterClass) {
		
		moduleContextList = FXCollections.observableArrayList(); 
		
		appDescriptor = new TAppDescriptor(appStarterClass);
				
		try {
			
			LOGGER.info("Start load app class: "+appStarterClass.getName());
			
			if(appDescriptor.getResourceNameList()!=null){
				LOGGER.info("Start loading app resources to the Internationalization Engine:");
				for(String bundle : appDescriptor.getResourceNameList()){
					TLanguage.addResourceBundle(appDescriptor.getUniversalUniqueIdentifier(), bundle, appStarterClass.getClassLoader());
					LOGGER.info(bundle + " added.");
				}
				LOGGER.info("Finish loading app resources to the Internationalization Engine.");
			}
			
			iEngine = TLanguage.getInstance(appDescriptor.getUniversalUniqueIdentifier());
								
			if(appDescriptor.getSecurityDescriptor()!=null){
				if(!TedrosContext.isUserAuthorized(appDescriptor.getSecurityDescriptor(), TAuthorizationType.APP_ACCESS)){
					LOGGER.info("App access disabled by security policy.");
					return;
				}
			}
			
			if(TedrosCoreUtil.isImplemented(appStarterClass, ITApplication.class)){
				LOGGER.info("Calling start method:");
				application = (ITApplication) appStarterClass.getDeclaredConstructor().newInstance();
				application.start();
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
			LOGGER.error(e.toString(), e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void stop() {
		application.stop();
		for(Class<? extends ITProcess> c : appDescriptor.getProcessList()){
			TedrosProcessManager.stopProcess(c);
		}
		this.moduleContextList.forEach(c->{
			c.stopModule();
		});
		this.moduleContextList.clear();
		this.moduleContextList = null;
		this.application = null;
		this.appDescriptor = null;
	}
	
	/**
	 * Return the context of the module class
	 * */
	public TModuleContext findModuleContext(Class<? extends ITModule> moduleClass){
		Optional<TModuleContext> op = moduleContextList.stream().filter(m->{
			return m.getModuleDescriptor().getType()==moduleClass;
		}).findFirst();
		
		return op.isPresent() 
				? op.get()
						: null;
	}
	
	
	/**
	 * Return the context of the loaded module
	 * */
	public TModuleContext findModuleContext(ITModule module){
		Optional<TModuleContext> op = moduleContextList.stream().filter(m->{
			return m.getModule()!=null && m.getModule().equals(module);
		}).findFirst();
		
		return op.isPresent() 
				? op.get()
						: null;
	}
	
	public boolean isModuleContextPresent(ITModule module){
		return moduleContextList.stream().filter(m->{
			return m.getModule()!=null && m.getModule().equals(module);
		}).findAny().isPresent();
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
