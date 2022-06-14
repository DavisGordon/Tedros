/**
 * 
 */
package com.tedros.tools.module.preferences;


import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.tools.module.preferences.model.TMimeTypeMV;
import com.tedros.tools.module.preferences.model.TPropertieMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.SETTINGS_MODULE_ID, appName="#{app.tools}", moduleName="#{module.preferences}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TSettingsModule extends TModule {
	
	
	@Override
	public void tStart() {
		
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.system.propertie}", 
				new TViewItem(TDynaGroupView.class, TPropertieMV.class, "#{view.propertie}"),
				new TViewItem(TDynaGroupView.class, TMimeTypeMV.class, "#{view.mimetype}")
				));
		
	}

}
