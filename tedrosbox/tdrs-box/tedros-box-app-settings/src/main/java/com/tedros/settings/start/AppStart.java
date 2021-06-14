package com.tedros.settings.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.settings.layout.CustomizarModule;
import com.tedros.settings.layout.icon.LayoutIconImageView;
import com.tedros.settings.layout.icon.LayoutMenuIconImageView;
import com.tedros.settings.security.TedrosAppSecurity;
import com.tedros.settings.security.icon.SecurityIconImageView;
import com.tedros.settings.security.icon.SecurityMenuIconImageView;

@TApplication(name="#{settings.app.name}", universalUniqueIdentifier=TConstant.UUI,
module = {@TModule(type=CustomizarModule.class, name="#{settings.module.name}", menu = "#{settings.menu.title}", 
					icon=LayoutIconImageView.class, menuIcon=LayoutMenuIconImageView.class),
		@TModule(type=TedrosAppSecurity.class, name="Security", menu = "#{settings.menu.title}", 
		icon=SecurityIconImageView.class, menuIcon=SecurityMenuIconImageView.class)})
@TResourceBundle(resourceName={"SettingLabels", "TCoreLabels"})
@TSecurity(id="T_CUSTOM", appName="#{settings.app.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
}
