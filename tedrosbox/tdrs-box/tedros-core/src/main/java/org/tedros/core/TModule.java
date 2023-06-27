package org.tedros.core;

import org.tedros.api.presenter.ITModelViewPresenter;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.context.InternalView;
import org.tedros.core.context.TModuleContext;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.model.ITModelView;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
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
	
	public String tCanStop() {
		TModuleContext context = TedrosAppManager.getInstance().getModuleContext(this);
		return context!=null ? context.canStop() : null;
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
		tShowView(TedrosContext.getViewBuilder().build(this));
	}


	@SuppressWarnings({ "rawtypes"})
	@Override
	public <M extends ITModelView> void tLookupAndShow(Class<M> modelViewClass) {
		if(modelViewClass == null)
			throw new IllegalArgumentException("The modelViewClass argument cannot be null"); 
		
		super.getChildren().forEach(n->{
			if(n instanceof ITView) {
				Object p = ((ITView)n).gettPresenter();
				if(p instanceof ITModelViewPresenter) {
					ITModelViewPresenter mvp = (ITModelViewPresenter) p;
					if(mvp.isLoadable(modelViewClass))
						((ITModelViewPresenter)p).lookupAndShow(modelViewClass);
				}
			}
		});
		
	}
	
	@SuppressWarnings({ "rawtypes"})
	@Override
	public <M extends ITModelView> void tLookupAndShow(M modelView) {
		if(modelView == null)
			throw new IllegalArgumentException("The modelView argument cannot be null"); 
		
		super.getChildren().forEach(n->{
			if(n instanceof ITView) {
				Object p = ((ITView)n).gettPresenter();
				if(p instanceof ITModelViewPresenter) {
					ITModelViewPresenter mvp = (ITModelViewPresenter) p;
					if(mvp.isLoadable(modelView.getClass()))
						((ITModelViewPresenter)p).lookupAndShow(modelView);
				}
			}
		});
		
	}
	
	@SuppressWarnings({ "rawtypes"})
	@Override
	public <M extends ITModelView> void tLookupAndShow(ObservableList<M> modelsView) {
		if(modelsView == null || modelsView.isEmpty())
			throw new IllegalArgumentException("The modelsView argument cannot be null or empty"); 
		
		super.getChildren().forEach(n->{
			if(n instanceof ITView) {
				Object p = ((ITView)n).gettPresenter();
				if(p instanceof ITModelViewPresenter) {
					ITModelViewPresenter mvp = (ITModelViewPresenter) p;
					if(mvp.isLoadable(modelsView.get(0).getClass()))
						((ITModelViewPresenter)p).lookupAndShow(modelsView);
				}
			}
		});
		
	}

}
