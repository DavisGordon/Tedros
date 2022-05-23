/**
 * 
 */
package com.tedros.settings.security;


import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.settings.security.model.TAuthorizationModelView;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.settings.security.model.TUserModelView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.SETTINGS_MODULE_ID, appName="#{settings.app.name}", moduleName="#{security.view.title}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TSettingsModule extends TModule {
	
	
	@Override
	public void tStart() {
		
		tShowView(new TGroupView<TGroupPresenter>(this, "#{security.view.title}", 
				new TViewItem(TDynaGroupView.class, TAuthorizationModelView.class, "#{label.authorization}"),
				new TViewItem(TDynaGroupView.class, TProfileModelView.class, "#{label.profile}"),
				new TViewItem(TDynaGroupView.class, TUserModelView.class, "#{label.user}")
				));
		
	}

}
