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
	
	/**
	 * @param moduleClass
	 * @return
	 */
	public TModuleContext getModuleContext(Class<? extends ITModule> moduleClass) {
		for(TAppContext a : this.getAppContexts()){
			TModuleContext mc = a.findModuleContext(moduleClass);
			if(mc!=null)
				return mc;
		}
		return null;
	}
	
	public void goToModule(Class<? extends ITModule> moduleClass) {
		String path = getModuleContext(moduleClass).getModuleDescriptor().getPath();
		TedrosContext.setPagePathProperty(path, true, true, true);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void goToModule(Class<? extends ITModule> moduleClass, Class<? extends ITModelView> modelViewClass) {
		Node v = (Node) TedrosContext.viewProperty().getValue();
		if(v instanceof ITModule && v.getClass()==moduleClass) {
			ITModule m = (ITModule) v;
			m.tLookupAndShowView(modelViewClass);
		}else {
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
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadInModule(Class<? extends ITModule> moduleClass, ITModelView modelView) {
		Node v = (Node) TedrosContext.viewProperty().getValue();
		if(v instanceof ITModule && v.getClass()==moduleClass) {
			ITModule m = (ITModule) v;
			m.tLookupViewAndLoadModelView(modelView);
		}else {
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
	}
	
	@SuppressWarnings({"rawtypes"})
	public void loadInModule(String modulePath, ITModelView modelView) {
		String path = TLanguage.getInstance().getString(modulePath);
		Node v = (Node) TedrosContext.viewProperty().getValue();
		if(v instanceof ITModule) {
			ITModule m = (ITModule) v;
			if(this.getModuleContext(m).getModuleDescriptor().getPath().equals(path))
				m.tLookupViewAndLoadModelView(modelView);
			else {
				listenView(modelView, path);
				TedrosContext.setPagePathProperty(path, true, true, true);
			}
		}else {
			listenView(modelView, path);
			TedrosContext.setPagePathProperty(path, true, true, true);
		}
	}

	/**
	 * @param modelView
	 * @param path
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void listenView(ITModelView modelView, String path) {
		ChangeListener<Node> chl = new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> a, Node o, Node n) {
				if(n instanceof ITModule) {
					ITModule m = (ITModule) n;
					if(getModuleContext(m).getModuleDescriptor().getPath().equals(path)) {
						m.tLookupViewAndLoadModelView(modelView);
						TedrosContext.viewProperty().removeListener(this);
					}
				}
			}
		};
		TedrosContext.viewProperty().addListener(chl);
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
