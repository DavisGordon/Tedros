/**
 * 
 */
package org.tedros.tools.module.notify.behaviour;

import org.tedros.core.TLanguage;
import org.tedros.core.TModule;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.notify.model.TNotify;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;
import org.tedros.tools.module.notify.model.TNotifyMV;

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
			closeBtn.setText(TLanguage.getInstance().getString(TFxKey.BUTTON_CLOSE));
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
