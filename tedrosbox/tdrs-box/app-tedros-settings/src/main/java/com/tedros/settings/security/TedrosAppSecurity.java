/**
 * 
 */
package com.tedros.settings.security;


import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.settings.security.model.TUserModelView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="T_CUSTOM_SECURITY", appName="#{settings.app.name}", moduleName="#{security.view.title}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TedrosAppSecurity extends TModule {
	
	
	@Override
	public void tStart() {
		
		tShowView(new TGroupView<TGroupPresenter>(this, "#{security.view.title}", 
				new TViewItem(TDynaView.class, TProfileModelView.class, "#{label.profile}"),
				new TViewItem(TDynaView.class, TUserModelView.class, "#{label.user}")
				));
		
	}

}
