package com.tedros.tools.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.tools.ToolsKey;
import com.tedros.tools.module.notify.TNotifyModule;
import com.tedros.tools.module.notify.icon.NotifyIcon;
import com.tedros.tools.module.notify.icon.NotifyMenuIcon;
import com.tedros.tools.module.preferences.TSettingsModule;
import com.tedros.tools.module.preferences.icon.SettingsIcon;
import com.tedros.tools.module.preferences.icon.SettingsMenuIcon;
import com.tedros.tools.module.scheme.SchemeModule;
import com.tedros.tools.module.scheme.icon.SchemeIcon;
import com.tedros.tools.module.scheme.icon.SchemeMenuIcon;
import com.tedros.tools.module.user.TUserModule;
import com.tedros.tools.module.user.icon.TUserIcon;
import com.tedros.tools.module.user.icon.TUserMenuIcon;
import com.tedros.tools.resource.AppResource;

@TApplication(name=ToolsKey.APP_TOOLS, packageName = "com.tedros.tools", universalUniqueIdentifier=TConstant.UUI,
module = {@TModule(type=SchemeModule.class, name=ToolsKey.MODULE_SCHEME, menu = ToolsKey.MENU_TOOLS, 
			icon=SchemeIcon.class, menuIcon=SchemeMenuIcon.class),
		@TModule(type=TSettingsModule.class, name=ToolsKey.MODULE_PREFERENCES, menu = ToolsKey.MENU_TOOLS, 
			icon=SettingsIcon.class, menuIcon=SettingsMenuIcon.class),
		@TModule(type=TUserModule.class, name=ToolsKey.MODULE_USER, menu = ToolsKey.MENU_TOOLS, 
			icon=TUserIcon.class, menuIcon=TUserMenuIcon.class),
		@TModule(type=TNotifyModule.class, name=ToolsKey.MODULE_NOTIFY, menu = ToolsKey.MENU_TOOLS, 
			icon=NotifyIcon.class, menuIcon=NotifyMenuIcon.class)
})
@TResourceBundle(resourceName={"TToolsLabels"})
@TSecurity(id=DomainApp.MNEMONIC, appName=ToolsKey.APP_TOOLS, allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		new AppResource().copyToFolder();
	}
	
}
