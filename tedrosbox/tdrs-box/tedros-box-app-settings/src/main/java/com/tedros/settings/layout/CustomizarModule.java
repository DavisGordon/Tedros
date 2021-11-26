package com.tedros.settings.layout;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.settings.layout.model.BackgroundImageModelView;
import com.tedros.settings.layout.model.PainelModelView;
import com.tedros.settings.start.TConstant;

@TSecurity(	id="T_CUSTOM_LAYOUT", appName="#{settings.app.name}", moduleName="#{settings.module.name}",	
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CustomizarModule extends TModule {
	
	@Override
	public void tStart() {
		
		TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(TConstant.UUI);
		tShowView(new TGroupView<>(this, iEngine.getString("#{settings.view.title}"), 
				new TViewItem(TDynaGroupView.class, PainelModelView.class, iEngine.getString("#{settings.form.name}"), true),
				new TViewItem(TDynaGroupView.class, BackgroundImageModelView.class, iEngine.getString("#{background.painel.title}"))));
		
	}

	


}
