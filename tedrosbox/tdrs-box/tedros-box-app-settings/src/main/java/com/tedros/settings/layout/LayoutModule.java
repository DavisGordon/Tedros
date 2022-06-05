package com.tedros.settings.layout;

import com.tedros.core.TLanguage;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.settings.layout.model.TBackgroundImageMV;
import com.tedros.settings.layout.model.TPanelMV;
import com.tedros.settings.layout.model.TMainColorMV;
import com.tedros.settings.start.TConstant;

@TSecurity(	id=DomainApp.LAYOUT_MODULE_ID, appName="#{app.settings}", moduleName="#{module.layout}",	
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class LayoutModule extends TModule {
	
	@Override
	public void tStart() {
		
		TLanguage iEngine = TLanguage.getInstance(TConstant.UUI);
		tShowView(new TGroupView<>(this, iEngine.getString("#{view.layout}"), 
				new TViewItem(TDynaGroupView.class, TMainColorMV.class, iEngine.getString("#{view.themes}"), true),
				new TViewItem(TDynaGroupView.class, TPanelMV.class, iEngine.getString("#{view.colors}"), true),
				new TViewItem(TDynaGroupView.class, TBackgroundImageMV.class, iEngine.getString("#{view.background}"))));
		
	}

	


}
