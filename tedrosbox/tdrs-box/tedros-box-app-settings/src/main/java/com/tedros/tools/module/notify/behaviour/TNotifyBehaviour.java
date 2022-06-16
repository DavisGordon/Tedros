/**
 * 
 */
package com.tedros.tools.module.notify.behaviour;

import com.tedros.core.notify.model.TNotify;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.tools.module.notify.model.TNotifyMV;

import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class TNotifyBehaviour extends TMasterCrudViewBehavior<TNotifyMV, TNotify> {

	private boolean enableLoadModels = true;
	
	@Override
	public void load() {
		TDynaPresenter<TNotifyMV> presenter = super.getPresenter();
		ObservableList<TNotifyMV> lst = presenter.getModelViews();
		if(lst!=null)
			this.enableLoadModels = false;
		super.load();
	}
	
	@Override
	public void loadModels() {
		if(this.enableLoadModels)
			super.loadModels();
	}
}
