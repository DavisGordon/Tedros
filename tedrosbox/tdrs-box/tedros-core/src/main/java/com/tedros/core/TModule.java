package com.tedros.core;

import com.tedros.core.context.InternalView;
import com.tedros.core.context.TModuleContext;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.presenter.view.ITView;

import javafx.scene.Node;

/**
 * A module of an application   
 * 
 * @author Davis Gordon
 * */
public abstract class TModule extends InternalView implements ITModule {
	
	public TModule() {
		super();
	}
	
	public TModule(double width, double height) {	
		super(width, height);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void tShowView(ITView view) {
		TModuleContext context = TedrosAppManager.getInstance().getModuleContext(this);
		if(context != null)
			context.addOpenedView(view);
		getChildren().add((Node)view);
		if(!view.gettPresenter().isViewLoaded())
			view.tLoad();
	}
	
	@Override
	public void tStop() {
		TModuleContext context = TedrosAppManager.getInstance().getModuleContext(this);
		if(context!=null){
			context.stopModule();
			//TedrosAppManager.getInstance().getAppContext(this).removeModuleContext(this);
			context = null;
		}
	}
}
