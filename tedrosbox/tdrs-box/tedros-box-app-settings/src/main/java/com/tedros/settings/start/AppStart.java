package com.tedros.settings.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.settings.layout.LayoutModule;
import com.tedros.settings.layout.icon.LayoutIconImageView;
import com.tedros.settings.layout.icon.LayoutMenuIconImageView;
import com.tedros.settings.properties.TSettingsModule;
import com.tedros.settings.properties.icon.Icon;
import com.tedros.settings.properties.icon.MenuIcon;

@TApplication(name="#{app.settings}", packageName = "com.tedros.settings", universalUniqueIdentifier=TConstant.UUI,
module = {@TModule(type=LayoutModule.class, name="#{module.layout}", menu = "#{menu.settings}", 
					icon=LayoutIconImageView.class, menuIcon=LayoutMenuIconImageView.class),
		@TModule(type=TSettingsModule.class, name="#{module.propertie}", menu = "#{menu.settings}", 
		icon=Icon.class, menuIcon=MenuIcon.class)})
@TResourceBundle(resourceName={"SettingLabels", "TCoreLabels"})
@TSecurity(id=DomainApp.MNEMONIC, appName="#{app.settings}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
}
