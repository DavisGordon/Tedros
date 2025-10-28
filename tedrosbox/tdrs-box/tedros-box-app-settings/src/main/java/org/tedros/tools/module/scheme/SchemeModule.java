package org.tedros.tools.module.scheme;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.model.TBackgroundImage;
import org.tedros.tools.module.scheme.model.TBackgroundImageMV;
import org.tedros.tools.module.scheme.model.TMainColor;
import org.tedros.tools.module.scheme.model.TMainColorMV;
import org.tedros.tools.module.scheme.model.TPanel;
import org.tedros.tools.module.scheme.model.TPanelMV;

@TView(title=ToolsKey.VIEW_LAYOUT,
items = {
	@TItem(title=ToolsKey.VIEW_THEMES, description=ToolsKey.VIEW_THEMES_DESC,
	model = TMainColor.class, modelView=TMainColorMV.class, groupHeaders=true),
	@TItem(title=ToolsKey.VIEW_COLORS, description=ToolsKey.VIEW_COLORS_DESC,
	model = TPanel.class, modelView=TPanelMV.class, groupHeaders=true),
	@TItem(title=ToolsKey.VIEW_BACKGROUND, description=ToolsKey.VIEW_BACKGROUND_DESC,
	model = TBackgroundImage.class, modelView=TBackgroundImageMV.class)
})
@TSecurity(id=DomainApp.LAYOUT_MODULE_ID, appName=ToolsKey.APP_TOOLS, 
moduleName=ToolsKey.MODULE_SCHEME, allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class SchemeModule extends TModule {
	
}
