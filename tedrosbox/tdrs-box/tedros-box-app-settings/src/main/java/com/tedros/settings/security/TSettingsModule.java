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
import com.tedros.settings.security.model.TAuthorizationMV;
import com.tedros.settings.security.model.TOwnerMV;
import com.tedros.settings.security.model.TProfileMV;
import com.tedros.settings.security.model.TUserMV;

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
				new TViewItem(TDynaGroupView.class, TOwnerMV.class, "#{owner.view}"),
				new TViewItem(TDynaGroupView.class, TAuthorizationMV.class, "#{label.authorization}"),
				new TViewItem(TDynaGroupView.class, TProfileMV.class, "#{label.profile}"),
				new TViewItem(TDynaGroupView.class, TUserMV.class, "#{label.user}")
				));
		
	}

}
