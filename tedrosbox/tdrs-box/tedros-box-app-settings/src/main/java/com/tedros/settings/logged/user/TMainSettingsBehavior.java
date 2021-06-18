package com.tedros.settings.logged.user;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.modal.TConfirmMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class TMainSettingsBehavior extends TSaveViewBehavior<TMainSettingsModelView, MainSettings> {

	private TInternationalizationEngine iEngine;
	
	@Override
	public void load() {
		
		super.load();
		
		iEngine = TInternationalizationEngine.getInstance(null);
		
		super.setSaveAction(new TPresenterAction<TDynaPresenter<TMainSettingsModelView>>() {

			@Override
			public boolean runBefore(TDynaPresenter<TMainSettingsModelView> presenter) {
				
				Node view = TedrosContext.getView();
		    	ITModule m = null;
		    	if(view != null && view instanceof ITModule)
		    		m = (ITModule) view;
		    	else if(view != null && view instanceof ScrollPane && ((ScrollPane)view).getContent() instanceof ITModule)
		    		m = (ITModule) ((ScrollPane)view).getContent();
		    	
		    	if(m!=null) {
			    	String msg = m.canStop();
			    	if(msg==null) {
			    		TedrosContext.exit();
			    	}else {
			    		
			    		ChangeListener<Number> chl = (a0, a1, a2) -> {
			    			TedrosContext.hideModal();
			    			if(a2.equals(1)) {
			    				TedrosContext.exit();
			    			}
			    		};
			    		
			    		TConfirmMessageBox confirm = new TConfirmMessageBox(msg);
			    		confirm.gettConfirmProperty().addListener(chl);
			    		TedrosContext.showModal(confirm);
			    	}
		    	}else
		    		TedrosContext.exit();
		    	
				return false;
			}

			@Override
			public void runAfter(TDynaPresenter<TMainSettingsModelView> presenter) {
				
			}
			
		});
		
		getView().gettProgressIndicator().setMargin(5);
	}

	
	
	@Override
	public void colapseAction() {
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(TMainSettingsModelView model) {
		return true;
	}

	@Override
	public void remove() {
	}


}
