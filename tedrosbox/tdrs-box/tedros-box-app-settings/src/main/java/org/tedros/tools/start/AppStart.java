package org.tedros.tools.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.notify.TNotifyModule;
import org.tedros.tools.module.notify.icon.NotifyIcon;
import org.tedros.tools.module.notify.icon.NotifyMenuIcon;
import org.tedros.tools.module.preferences.TSettingsModule;
import org.tedros.tools.module.preferences.icon.SettingsIcon;
import org.tedros.tools.module.preferences.icon.SettingsMenuIcon;
import org.tedros.tools.module.scheme.SchemeModule;
import org.tedros.tools.module.scheme.icon.SchemeIcon;
import org.tedros.tools.module.scheme.icon.SchemeMenuIcon;
import org.tedros.tools.module.user.TUserModule;
import org.tedros.tools.module.user.icon.TUserIcon;
import org.tedros.tools.module.user.icon.TUserMenuIcon;
import org.tedros.tools.resource.AppResource;

@TApplication(name=ToolsKey.APP_TOOLS, packageName = "org.tedros.tools", universalUniqueIdentifier=TConstant.UUI,
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
