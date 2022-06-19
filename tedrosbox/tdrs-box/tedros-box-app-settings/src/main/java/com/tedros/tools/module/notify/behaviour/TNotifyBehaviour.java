/**
 * 
 */
package com.tedros.tools.module.notify.behaviour;

import com.tedros.core.TLanguage;
import com.tedros.core.TModule;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.notify.model.TNotify;
import com.tedros.fxapi.control.TButton;
import com.tedros.fxapi.domain.TLabelKey;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;
import com.tedros.tools.module.notify.model.TNotifyMV;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;

/**
 * @author Davis Gordon
 *
 */
public class TNotifyBehaviour extends TMasterCrudViewBehavior<TNotifyMV, TNotify> {

	private boolean enableLoadModels = true;
	
	@Override
	public void load() {
		TDynaPresenter<TNotifyMV> presenter = super.getPresenter();
		//ObservableList<TNotifyMV> lst = presenter.getModelViews();
		if(super.getModels()!=null) {
			TButton closeBtn = new TButton();
			closeBtn.setText(TLanguage.getInstance().getString(TLabelKey.BUTTON_CLOSE));
			((TMasterCrudViewDecorator<TNotifyMV>)presenter.getDecorator()).addItemInTHeaderToolBar(closeBtn);
			EventHandler<ActionEvent> ev = e ->{
				super.invalidate();
				TedrosAppManager.getInstance()
				.getModuleContext((TModule)TedrosContext.getView()).getCurrentViewContext()
				.getPresenter().getView().tHideModal();
			};
			super.getListenerRepository().add("closeBtnEvh", ev);
			closeBtn.setOnAction(new WeakEventHandler<>(ev));
			this.enableLoadModels = false;
		}
		super.load();
	}
	
	@Override
	public void loadModels() {
		if(this.enableLoadModels)
			super.loadModels();
		else
			super.loadListView();
	}
}
