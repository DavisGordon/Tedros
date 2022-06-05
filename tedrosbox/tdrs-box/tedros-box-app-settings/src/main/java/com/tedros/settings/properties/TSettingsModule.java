/**
 * 
 */
package com.tedros.settings.properties;


import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.settings.properties.model.TAuthorizationMV;
import com.tedros.settings.properties.model.TProfileMV;
import com.tedros.settings.properties.model.TPropertieMV;
import com.tedros.settings.properties.model.TUserMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.SETTINGS_MODULE_ID, appName="#{app.settings}", moduleName="#{module.propertie}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TSettingsModule extends TModule {
	
	
	@Override
	public void tStart() {
		
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.system.propertie}", 
				new TViewItem(TDynaGroupView.class, TPropertieMV.class, "#{view.propertie}"),
				new TViewItem(TDynaGroupView.class, TAuthorizationMV.class, "#{view.authorization}"),
				new TViewItem(TDynaGroupView.class, TProfileMV.class, "#{view.profile}"),
				new TViewItem(TDynaGroupView.class, TUserMV.class, "#{view.user}")
				));
		
	}

}
