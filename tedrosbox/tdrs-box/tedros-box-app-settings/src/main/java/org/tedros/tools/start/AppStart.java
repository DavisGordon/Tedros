package org.tedros.tools.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.TAiModule;
import org.tedros.tools.module.notify.TNotifyModule;
import org.tedros.tools.module.preferences.TSettingsModule;
import org.tedros.tools.module.scheme.SchemeModule;
import org.tedros.tools.module.user.TUserModule;
import org.tedros.tools.resource.AppResource;

@TApplication(name=ToolsKey.APP_TOOLS, 
module = {
	@TModule(type=SchemeModule.class, name=ToolsKey.MODULE_SCHEME, 
	menu = ToolsKey.MENU_TOOLS, description=ToolsKey.MODULE_SCHEME_DESC, 
	icon=TConstant.ICONS_FOLDER+"scheme_icon.png", 
	menuIcon=TConstant.ICONS_FOLDER+"scheme_menu_icon.png"),
	@TModule(type=TSettingsModule.class, name=ToolsKey.MODULE_PREFERENCES, 
	menu = ToolsKey.MENU_TOOLS, description=ToolsKey.MODULE_PREFERENCES_DESC, 
	icon=TConstant.ICONS_FOLDER+"preferences_icon.png", 
	menuIcon=TConstant.ICONS_FOLDER+"preferences_menu_icon.png"),
	@TModule(type=TUserModule.class, name=ToolsKey.MODULE_USER,
	menu = ToolsKey.MENU_TOOLS, description=ToolsKey.MODULE_USER_DESC, 
	icon=TConstant.ICONS_FOLDER+"user_icon.png", 
	menuIcon=TConstant.ICONS_FOLDER+"user_menu_icon.png"),
	@TModule(type=TNotifyModule.class, name=ToolsKey.MODULE_NOTIFY, 
	menu = ToolsKey.MENU_TOOLS, description=ToolsKey.MODULE_NOTIFY_DESC, 
	icon=TConstant.ICONS_FOLDER+"notify_icon.png", 
	menuIcon=TConstant.ICONS_FOLDER+"notify_menu_icon.png"),
	@TModule(type=TAiModule.class, name=ToolsKey.MODULE_AI, 
	menu = ToolsKey.MENU_TOOLS, description=ToolsKey.MODULE_AI_DESC,
	icon=TConstant.ICONS_FOLDER+"icon_art_int.png", 
	menuIcon=TConstant.ICONS_FOLDER+"menu_art_int.png")
}, packageName = "org.tedros.tools", universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"TToolsLabels"})
@TSecurity(id=DomainApp.MNEMONIC, appName=ToolsKey.APP_TOOLS, allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		new AppResource().copyToFolder();
	}

	@Override
	public void stop() {
		
	}
	
}
