/**
 * 
 */
package com.tedros.tools.module.user;


import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.tools.ToolsKey;
import com.tedros.tools.module.user.model.TAuthorizationMV;
import com.tedros.tools.module.user.model.TProfileMV;
import com.tedros.tools.module.user.model.TUserMV;

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
