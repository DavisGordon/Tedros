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
import com.tedros.tools.ToolsKey;
import com.tedros.tools.module.preferences.model.TMimeTypeMV;
import com.tedros.tools.module.preferences.model.TPropertieMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.SETTINGS_MODULE_ID, appName=ToolsKey.APP_TOOLS, 
moduleName=ToolsKey.MODULE_PREFERENCES, allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TSettingsModule extends TModule {
	
	@Override
	public void tStart() {
		
		tShowView(new TGroupView<TGroupPresenter>(this, ToolsKey.VIEW_SYSTEM_SETTINGS, 
				new TViewItem(TDynaGroupView.class, TPropertieMV.class, ToolsKey.VIEW_PROPERTIE),
				new TViewItem(TDynaGroupView.class, TMimeTypeMV.class, ToolsKey.VIEW_MIMETYPE)
				));
	}
}
