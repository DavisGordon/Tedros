/**
 * 
 */
package org.tedros.tools.module.user;


import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.model.TAuthorizationMV;
import org.tedros.tools.module.user.model.TProfileMV;
import org.tedros.tools.module.user.model.TUserMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.USER_MODULE_ID, appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_USER, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TUserModule extends TModule {
	
	
	@Override
	public void tStart() {
		
		tShowView(new TGroupView<TGroupPresenter>(this, ToolsKey.VIEW_USER_SETTINGS,
				new TViewItem(TDynaGroupView.class, TAuthorizationMV.class, ToolsKey.VIEW_AUTHORIZATION),
				new TViewItem(TDynaGroupView.class, TProfileMV.class, ToolsKey.VIEW_PROFILE),
				new TViewItem(TDynaGroupView.class, TUserMV.class, ToolsKey.VIEW_USER)
				));
		
	}

}
