/**
 * 
 */
package org.tedros.core.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The app loader.
 * 
 * @author davis.dun
 */
abstract class TedrosAppLoader {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	protected final Map<String, TEntry<TAppContext>> entrys;
	
	private ObservableList<TAppContext> appContexts;
	
	protected TedrosAppLoader(){
		entrys = new HashMap<String, TEntry<TAppContext>>();
		appContexts = FXCollections.observableArrayList();
	}
	
	@SuppressWarnings("rawtypes")
	protected void addApplication(Class appStarterClass){
		if(!appContexts.stream().anyMatch(p->{
			return p.getAppDescriptor().getAppStarterClass() == appStarterClass;
		})) {
			appContexts.add(new TAppContext(appStarterClass));
			LOGGER.info("App "+appStarterClass.getCanonicalName()+" added!");
		}
	}
	
	void stopAll() {
		appContexts
		.forEach(c->{
			c.stop();
		});
		entrys.clear();
		appContexts.clear();
	}
	
	public ObservableList<TAppContext> getAppContexts() {
		return appContexts;
	}
	
	protected List<TModuleContext> getModuleContexts(){
		
		List<TModuleContext> lst = new ArrayList<TModuleContext>();
		
		for (TAppContext tAppContext : appContexts)
			lst.addAll(tAppContext.getModulesContext());
		
		return lst;
	}
}
