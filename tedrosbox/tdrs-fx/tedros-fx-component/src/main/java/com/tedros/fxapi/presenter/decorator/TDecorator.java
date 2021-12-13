package com.tedros.fxapi.presenter.decorator;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;

import javafx.scene.layout.StackPane;

@SuppressWarnings("rawtypes")
public abstract class TDecorator<P extends ITPresenter> 
implements ITDecorator<P> {

	private P presenter;
	private StackPane screenSaverPane;
	
	protected TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null); 

	@Override
	public StackPane getScreenSaverPane() {
		if(screenSaverPane==null){
			screenSaverPane = new StackPane();
			screenSaverPane.setId("t-screen-saver");
		}
		return screenSaverPane;
	}
	
	@Override
	public void showScreenSaver() {
		getView().gettFormSpace().getChildren().add(getScreenSaverPane());
	}

	@Override
	public P getPresenter() {
		return presenter;
	}

	@Override
	public void setPresenter(P presenter) {
		this.presenter = presenter;
		iEngine.setCurrentUUID(getApplicationUUID());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V extends ITView> V getView() {
		return (V) this.presenter.getView();
	}	
	
	public String getApplicationUUID() {
		String uuid = null;
		ITModule module = getPresenter().getModule();
		if(module!=null){
			uuid = TedrosAppManager.getInstance().getModuleContext(module).getModuleDescriptor().getApplicationUUID();
		}
		return uuid;
	}
	
}
