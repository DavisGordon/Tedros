/**
 * 
 */
package org.tedros.tools.module.preferences;


import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.preferences.model.TMimeTypeMV;
import org.tedros.tools.module.preferences.model.TPropertieMV;

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
