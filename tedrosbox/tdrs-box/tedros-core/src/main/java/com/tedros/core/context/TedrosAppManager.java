package com.tedros.core.context;

import com.tedros.core.ITModule;
import com.tedros.core.TLanguage;
import com.tedros.core.model.ITModelView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
/**
 * App manager
 * */
public final class TedrosAppManager extends TedrosAppLoader {

	private static TedrosAppManager instance;
	/**
	 * Return an instance
	 * */
	public static TedrosAppManager getInstance(){
		if(instance==null)
			instance = new TedrosAppManager();
		return instance;
	}
	
	public void goToModule(Class<? extends ITModule> moduleClass) {
		StringBuilder pp = new StringBuilder();
		getAppContexts()
		.stream().forEach(c->{
			c.getModulesContext().stream()
			.forEach(m->{
				if(m.getModuleDescriptor().getType()==moduleClass) {
					pp.append(TLanguage.getInstance().getString("#{tedros.modules}/"));
					pp.append(m.getModuleDescriptor().getApplicationName()+"/");
					pp.append(m.getModuleDescriptor().getMenu()+"/");
					pp.append(m.getModuleDescriptor().getModuleName());
				}
			});
		});
		TedrosContext.setPagePathProperty(pp.toString(), true, false, true);
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void goToModule(Class<? extends ITModule> moduleClass, Class<? extends ITModelView> modelViewClass) {
		ChangeListener<Node> chl = new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> a, Node o, Node n) {
				if(n instanceof ITModule && n.getClass()==moduleClass) {
					ITModule m = (ITModule) n;
					m.tLookupAndShowView(modelViewClass);
					TedrosContext.viewProperty().removeListener(this);
				}
			}
		};
		TedrosContext.viewProperty().addListener(chl);
		goToModule(moduleClass);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadInModule(Class<? extends ITModule> moduleClass, ITModelView modelView) {
		ChangeListener<Node> chl = new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> a, Node o, Node n) {
				if(n instanceof ITModule && n.getClass()==moduleClass) {
					ITModule m = (ITModule) n;
					m.tLookupViewAndLoadModelView(modelView);
					TedrosContext.viewProperty().removeListener(this);
				}
			}
		};
		TedrosContext.viewProperty().addListener(chl);
		goToModule(moduleClass);
	}
	
	/**
	 * Returns the module context of the module
	 * */
	public TModuleContext getModuleContext(ITModule module){
		TModuleContext context = null;
		for (TAppContext appContext : getAppContexts()) {
			context = appContext.findModuleContext(module);
			if(context!=null)
				return context;
		}
		return null;
	}
	/**
	 * Returns the app context of the module
	 * */
	public TAppContext getAppContext(ITModule module){
		for (TAppContext appContext : getAppContexts()) {
			if(appContext.isModuleContextPresent(module))
				return appContext;
		}
		return null;
	}
	/**
	 * Remove the module context
	 * */
	public void removeModuleContext(ITModule module){
		for (TAppContext appContext : getAppContexts()) {
			if(appContext.isModuleContextPresent(module)){
				appContext.removeModuleContext(module);
			}
		}
	}
}	
