package org.tedros.tools.logged.user;

import org.tedros.core.ITModule;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.modal.TConfirmMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.entity.behavior.TSaveViewBehavior;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class TMainSettingsBehavior extends TSaveViewBehavior<TMainSettingsModelView, MainSettings> {
	
	@Override
	public void load() {
		
		super.load();
		
		addAction(new TPresenterAction(TActionType.SAVE) {

			@Override
			public boolean runBefore() {
				
				Node view = TedrosContext.getView();
		    	ITModule m = null;
		    	if(view != null && view instanceof ITModule itModule)
		    		m = itModule;
		    	else if(view != null && view instanceof ScrollPane sp 
		    			&& sp.getContent() instanceof ITModule itModule)
		    		m = itModule;
		    	
		    	if(m!=null) {
			    	String msg = m.tCanStop();
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
			    		confirm.tConfirmProperty().addListener(chl);
			    		TedrosContext.showModal(confirm);
			    	}
		    	}else
		    		TedrosContext.exit();
		    	
				return false;
			}

			@Override
			public void runAfter() {
				
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
