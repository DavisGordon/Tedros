/**
 * 
 */
package com.tedros.core.context;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.ITModule;
import com.tedros.core.TModuleDescriptor;
import com.tedros.core.presenter.view.ITView;

/**
 * @author Davis Gordon
 *
 */
public final class TModuleContext implements Comparable<TModuleContext>{
	
	private TAppContext appContext;
	private ITModule module;
	//private ObservableList<TViewContext> viewContexts;
	private List<TViewContext> viewContexts;
	private TEntry<Object> tEntry;
	private final TModuleDescriptor descriptor;
	
	private TViewContext currentViewContext;
	
	public TModuleContext(TAppContext appContext, TModuleDescriptor descriptor){
		this.appContext = appContext;
		this.tEntry = new TEntry<>();
		this.descriptor = descriptor;
		this.viewContexts =  new ArrayList<>();//FXCollections.observableArrayList();
	}
	
	public void setContext(TEntry<Object> entry){
		if(tEntry!=null){
			ITModule module = getModule();
			if(module != null)
				module.tStop();
		}
		this.tEntry = entry;
	}
	
	public TModuleDescriptor getModuleDescriptor() {
		return descriptor;
	}
	
	public void buildModule() throws InstantiationException, IllegalAccessException{
		module = descriptor.getType().newInstance();
	}
	
	public ITModule getModule(){
		return module;
	}
	
	@SuppressWarnings("rawtypes")
	public void addOpenedView(ITView view){
		final TViewContext ctx = new TViewContext(this, view);
		this.currentViewContext = ctx;
		this.viewContexts.add(ctx);
	}
	
	public void stopModule(){
		for (TViewContext tViewContext : viewContexts) {
			tViewContext.stop();
		}
		viewContexts.clear();
	}
	
	public void starModule(){
		getModule().tStart();
	}
	
	public Object getEntry(String key){
		return (StringUtils.isNotBlank(key) && tEntry!=null) 
				? tEntry.getEntry(key)
						: null;
	}
	
	public Object setEntry(String key, Object value){
		if(StringUtils.isNotBlank(key) && value!=null && tEntry!=null) 
			tEntry.addEntry(key, value);
		return null;
	}
	
	@Override
	public int compareTo(TModuleContext o) {
		String thisStr = descriptor.getApplicationName() + " " + descriptor.getMenu() + " " + descriptor.getModuleName();
		String objStr = o.getModuleDescriptor().getApplicationName() + " " + o.getModuleDescriptor().getMenu() + " " + o.getModuleDescriptor().getModuleName();
		return thisStr.compareTo(objStr);
	}

	public TAppContext getAppContext() {
		return appContext;
	}

	public TViewContext getCurrentViewContext() {
		return currentViewContext;
	}

}
