package com.tedros.tools.module.scheme;

import com.tedros.core.TLanguage;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.tools.ToolsKey;
import com.tedros.tools.module.scheme.model.TBackgroundImageMV;
import com.tedros.tools.module.scheme.model.TMainColorMV;
import com.tedros.tools.module.scheme.model.TPanelMV;
import com.tedros.tools.start.TConstant;

@TSecurity(id=DomainApp.LAYOUT_MODULE_ID, appName=ToolsKey.APP_TOOLS, 
moduleName=ToolsKey.MODULE_SCHEME, allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class SchemeModule extends TModule {
	
	@Override
	public void tStart() {
		
		TLanguage iEngine = TLanguage.getInstance(TConstant.UUI);
		tShowView(new TGroupView<>(this, iEngine.getString(ToolsKey.VIEW_LAYOUT), 
				new TViewItem(TDynaGroupView.class, TMainColorMV.class, iEngine.getString(ToolsKey.VIEW_THEMES), true),
				new TViewItem(TDynaGroupView.class, TPanelMV.class, iEngine.getString(ToolsKey.VIEW_COLORS), true),
				new TViewItem(TDynaGroupView.class, TBackgroundImageMV.class, iEngine.getString(ToolsKey.VIEW_BACKGROUND))));
	}
}
