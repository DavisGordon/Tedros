package com.tedros.core;

import com.tedros.core.context.InternalView;
import com.tedros.core.context.TModuleContext;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITGroupPresenter;
import com.tedros.core.presenter.view.ITGroupView;
import com.tedros.core.presenter.view.ITView;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

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
		
		FadeTransition ft = new FadeTransition(Duration.millis(800), (Node) view);
	    ft.setFromValue(0.5);
	    ft.setToValue(1);
	    ft.play();
	    if(!view.gettPresenter().isViewLoaded()) {
			view.tLoad();
		}
	}
	
	public String canStop() {
		TModuleContext context = TedrosAppManager.getInstance().getModuleContext(this);
		return context.canStop();
	}
	
	@Override
	public boolean tStop() {
		TModuleContext context = TedrosAppManager.getInstance().getModuleContext(this);
		boolean flag = true;
		if(context!=null){
			flag = context.stopModule();
			context = null;
		}
		return flag;
	}

	@Override
	public void tStart() {
		
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public <M extends ITModelView> void tLookupViewAndLoadModelView(M modelView) {
		super.getChildren().forEach(n->{
			if(n instanceof ITView) {
				((ITView)n).gettPresenter().loadModelView(modelView);
			}
		});
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <M extends ITModelView> void tLookupAndShowView(Class<M> modelViewClass) {
		super.getChildren().forEach(n->{
			if(n instanceof ITGroupView) {
				((ITGroupPresenter)((ITGroupView)n).gettPresenter()).lookupAndShowView(modelViewClass);
			}
		});
		
	}
}
