package org.tedros.tools.module.scheme;

import org.tedros.core.TLanguage;
import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.model.TBackgroundImageMV;
import org.tedros.tools.module.scheme.model.TMainColorMV;
import org.tedros.tools.module.scheme.model.TPanelMV;
import org.tedros.tools.start.TConstant;

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
