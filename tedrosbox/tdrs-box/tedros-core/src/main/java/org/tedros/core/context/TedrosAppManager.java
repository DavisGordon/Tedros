package org.tedros.core.context;

import java.util.function.Consumer;

import org.tedros.api.presenter.ITDynaPresenter;
import org.tedros.api.presenter.ITGroupPresenter;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.behavior.ITDynaViewSimpleBaseBehavior;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
/**
 * App manager
 * */
public final class TedrosAppManager extends TedrosAppLoader {

	private static TedrosAppManager instance;
	
	private TedrosAppManager() {
		
	}
	/**
	 * Return an instance
	 * */
	public static TedrosAppManager getInstance(){
		if(instance==null)
			instance = new TedrosAppManager();
		return instance;
	}
	
	public ITModule getCurrentModule() {
		
		Node view = TedrosContext.getView();
		
    	if(view != null && view instanceof ITModule m)
    		return m;
    	else if(view != null && view instanceof ScrollPane sp && sp.getContent() instanceof ITModule m)
    		return m;
    	
    	return null;
	}
	
	@SuppressWarnings("rawtypes")
	public ITPresenter getCurrentPresenter() {
		ITModule m = getCurrentModule();
		if(m==null)
			return null;
				
		TModuleContext mc = getModuleContext(m);
		if(mc==null)
			return null;
		
		TViewContext vc = mc.getCurrentViewContext();
		return vc.getPresenter();
	}
	
	@SuppressWarnings("rawtypes")
	public ITView getCurrentView() {
		ITPresenter p = getCurrentPresenter();
		if(p instanceof ITGroupPresenter itGroupPresenter) 
			return itGroupPresenter.getSelectedView();
		
		if(p instanceof ITDynaPresenter itDynaPresenter) 
			return itDynaPresenter.getDecorator().getView();
		
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TViewDescriptor getCurrentViewDescriptor() {
		ITPresenter p = getCurrentPresenter();
		ITDynaViewSimpleBaseBehavior b = null;
		Class<ITModel> model;
		Class<ITModelView> modelView;
		if(p instanceof ITGroupPresenter itGroupPresenter) {
			ITView ov = itGroupPresenter.getSelectedView();
			if(ov!=null) {
				ITDynaPresenter dp = (ITDynaPresenter) ov.gettPresenter();
				b = (ITDynaViewSimpleBaseBehavior) dp.getBehavior();
			}
		}
		
		if(p instanceof ITDynaPresenter itDynaPresenter) {
			b = (ITDynaViewSimpleBaseBehavior) itDynaPresenter.getBehavior();
		}
		if(b!=null) {
			model = b.getModelClass();
			modelView = b.getModelViewClass();
			ITModule m = getCurrentModule();
			
			if(m==null)
				return null;
			
			TModuleContext mc = getModuleContext(m);
			if(mc==null)
				return null;			
			
			for(TViewDescriptor vds : mc.getModuleDescriptor().getViewDescriptors())
				if(vds.getModel()==model && vds.getModelView()==modelView)
					return vds;
		}
		return null;
	}
	
	public TViewDescriptor getViewDescriptor(String viewPath) {
		for(TAppContext actx : getAppContexts())
			for(TModuleContext mctx : actx.getModulesContext())
				for(TViewDescriptor vds : mctx.getModuleDescriptor().getViewDescriptors())
					if(vds.getPath().equals(viewPath) && vds.getModel()!=null) 
						return vds;
		return null;
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
	
	@SuppressWarnings("rawtypes")
	public TViewDescriptor getViewDescriptor(Class<? extends ITModelView> modelViewClass, Class<? extends ITModel> modelClass) {
		for(TAppContext a : this.getAppContexts()){
			for(TModuleContext mctx : a.getModulesContext()) {
				for(TViewDescriptor vds : mctx.getModuleDescriptor().getViewDescriptors()) {
					if(vds.getModel()!=null && vds.getModel()==modelClass 
							&& vds.getModelView()!=null && vds.getModelView()==modelViewClass)
						return vds;
				}
			}
		}
		return null;
	}
	
	public TModuleContext getModuleContext(@SuppressWarnings("rawtypes") Class<? extends ITModelView> modelViewClass, Class<? extends ITModel> modelClass) {
		for(TAppContext a : this.getAppContexts()){
			for(TModuleContext mctx : a.getModulesContext()) {
				for(TViewDescriptor vds : mctx.getModuleDescriptor().getViewDescriptors()) {
					if(vds.getModel()!=null && vds.getModel()==modelClass 
							&& vds.getModelView()!=null && vds.getModelView()==modelViewClass)
						return mctx;
				}
			}
		}
		return null;
	}
	
	public void goToModule(Class<? extends ITModule> moduleClass) {
		
		TModuleContext mc = getModuleContext(moduleClass);
		if(mc==null)
			return;
		
		String path = mc.getModuleDescriptor().getPath();
		TedrosContext.setPagePathProperty(path, true, true, true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void goToModule(Class<? extends ITModule> moduleClass, Class<? extends ITModelView> modelViewClass) {
		Node v = (Node) TedrosContext.viewProperty().getValue();
		if(v instanceof ITModule itModule && v.getClass()==moduleClass) {
			itModule.tLookupAndShow(modelViewClass);
		}else {
			ChangeListener<Node> chl = new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> a, Node o, Node n) {
					if(n instanceof ITModule itModule && n.getClass()==moduleClass) {
						itModule.tLookupAndShow(modelViewClass);
						TedrosContext.viewProperty().removeListener(this);
					}
				}
			};
			TedrosContext.viewProperty().addListener(chl);
			goToModule(moduleClass);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void goToModule(Class<? extends ITModule> moduleClass, Class<? extends ITModelView> modelViewClass, Consumer<ITModule> consumer) {
		Node v = (Node) TedrosContext.viewProperty().getValue();
		if(v instanceof ITModule itModule && v.getClass()==moduleClass) {
			itModule.tLookupAndShow(modelViewClass);
			consumer.accept(itModule);
		}else {
			ChangeListener<Node> chl = new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> a, Node o, Node n) {
					if(n instanceof ITModule itModule && n.getClass()==moduleClass) {
						itModule.tLookupAndShow(modelViewClass);
						TedrosContext.viewProperty().removeListener(this);
						consumer.accept(itModule);
					}
				}
			};
			TedrosContext.viewProperty().addListener(chl);
			goToModule(moduleClass);
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void loadInModule(Class<? extends ITModule> moduleClass, ITModelView modelView) {
		loadIn(moduleClass, m->{
			m.tLookupAndShow(modelView);
		});
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void loadInModule(Class<? extends ITModule> moduleClass, ObservableList<? extends ITModelView> modelsView) {
		loadIn(moduleClass, m->{
			m.tLookupAndShow(modelsView);
		});
	}
	
	@SuppressWarnings({ "unchecked"})
	private void loadIn(Class<? extends ITModule> moduleClass, Consumer<ITModule> f) {
		Node v = (Node) TedrosContext.viewProperty().getValue();
		if(v instanceof ITModule itModule && v.getClass()==moduleClass) {
			f.accept(itModule);
		}else {
			ChangeListener<Node> chl = new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> a, Node o, Node n) {
					if(n instanceof ITModule itModule && n.getClass()==moduleClass) {
						f.accept(itModule);
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
		loadIn(modulePath, m->{
			m.tLookupAndShow(modelView);
		});
	}
	
	@SuppressWarnings({"rawtypes"})
	public void loadInModule(String modulePath, ObservableList<? extends ITModelView> modelsView) {
		loadIn(modulePath, m->{
			m.tLookupAndShow(modelsView);
		});
	}
	
	private void loadIn(String modulePath, Consumer<ITModule> f) {
		String path = TLanguage.getInstance().getString(modulePath);
		Node v = (Node) TedrosContext.viewProperty().getValue();
		if(v instanceof ITModule itModule) {			
			TModuleContext mc = this.getModuleContext(itModule);
			if(mc!=null && mc.getModuleDescriptor().getPath().equals(path))
				f.accept(itModule);
			else {
				listenView(f, path);
				TedrosContext.setPagePathProperty(path, true, true, true);
			}
		}else {
			listenView(f, path);
			TedrosContext.setPagePathProperty(path, true, true, true);
		}
	}

	/**
	 * @param modelView
	 * @param path
	 */
	@SuppressWarnings({ "unchecked"})
	public void listenView(Consumer<ITModule> f, String path) {
		ChangeListener<Node> chl = new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> a, Node o, Node n) {
				if(n instanceof ITModule itModule) {
					TModuleContext mc = getModuleContext(itModule); 
					if(mc!=null && mc.getModuleDescriptor().getPath().equals(path)) {
						f.accept(itModule);
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
