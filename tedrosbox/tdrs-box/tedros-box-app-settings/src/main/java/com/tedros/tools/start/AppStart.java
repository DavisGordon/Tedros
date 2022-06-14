package com.tedros.tools.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
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

@TApplication(name="#{app.tools}", packageName = "com.tedros.tools", universalUniqueIdentifier=TConstant.UUI,
module = {@TModule(type=SchemeModule.class, name="#{module.scheme}", menu = "#{menu.tools}", 
			icon=SchemeIcon.class, menuIcon=SchemeMenuIcon.class),
		@TModule(type=TSettingsModule.class, name="#{module.preferences}", menu = "#{menu.tools}", 
			icon=SettingsIcon.class, menuIcon=SettingsMenuIcon.class),
		@TModule(type=TUserModule.class, name="#{module.user}", menu = "#{menu.tools}", 
			icon=TUserIcon.class, menuIcon=TUserMenuIcon.class),
		@TModule(type=TNotifyModule.class, name="#{module.notify}", menu = "#{menu.tools}", 
			icon=NotifyIcon.class, menuIcon=NotifyMenuIcon.class)
})
@TResourceBundle(resourceName={"TToolsLabels", "TCommonLabels", "TCoreLabels"})
@TSecurity(id=DomainApp.MNEMONIC, appName="#{app.tools}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		
	}
	
}
