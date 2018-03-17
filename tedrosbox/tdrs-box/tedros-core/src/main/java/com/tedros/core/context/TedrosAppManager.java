package com.tedros.core.context;

import com.tedros.core.ITModule;

public final class TedrosAppManager extends TedrosAppLoader {

	private static TedrosAppManager instance;
	
	public static TedrosAppManager getInstance(){
		if(instance==null)
			instance = new TedrosAppManager();
		return instance;
	}
	
	public TModuleContext getModuleContext(ITModule module){
		TModuleContext context = null;
		for (TAppContext appContext : getAppContexts()) {
			context = appContext.findModuleContext(module);
			if(context!=null)
				return context;
		}
		return null;
	}
	
	public TAppContext getAppContext(ITModule module){
		for (TAppContext appContext : getAppContexts()) {
			if(appContext.isModuleContextPresent(module))
				return appContext;
		}
		return null;
	}
	
	public void closeModuleContext(ITModule module){
		for (TAppContext appContext : getAppContexts()) {
			if(appContext.isModuleContextPresent(module)){
				appContext.removeModuleContext(module);
			}
		}
	}
}	
