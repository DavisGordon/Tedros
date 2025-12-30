/**
 * 
 */
package org.tedros.core.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.util.TLoggerUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The app loader.
 * 
 * @author davis.dun
 */
abstract class TedrosAppLoader {

	private static final Logger LOGGER = TLoggerUtil.getLogger(TedrosAppLoader.class);
	
	protected final Map<String, TEntry<TAppContext>> entrys;
	
	private ObservableList<TAppContext> appContexts;
	
	protected TedrosAppLoader(){
		entrys = new HashMap<>();
		appContexts = FXCollections.observableArrayList();
	}
	
	@SuppressWarnings("rawtypes")
	protected void addApplication(Class appStarterClass){
		if(appContexts.stream().noneMatch(p->p.getAppDescriptor().getAppStarterClass() == appStarterClass)) {
			appContexts.add(new TAppContext(appStarterClass));
			LOGGER.info("App {}  added!", appStarterClass.getCanonicalName());
		}
	}
	
	void stopAll() {
		appContexts
		.forEach(TAppContext::stop);
		entrys.clear();
		appContexts.clear();
	}
	
	public ObservableList<TAppContext> getAppContexts() {
		return appContexts;
	}
	
	protected List<TModuleContext> getModuleContexts(){
		
		List<TModuleContext> lst = new ArrayList<>();
		
		for (TAppContext tAppContext : appContexts)
			lst.addAll(tAppContext.getModulesContext());
		
		return lst;
	}
}
