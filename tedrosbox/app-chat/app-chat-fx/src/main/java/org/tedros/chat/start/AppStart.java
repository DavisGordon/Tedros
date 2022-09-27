package org.tedros.chat.start;

import org.tedros.chat.CHATKey;
import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.module.client.TChatModule;
import org.tedros.chat.module.server.ChatSettingModule;
import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;

/**
 * The app start class.
 * 
 * @author Davis Dun
 * */
@TApplication(name=CHATKey.APP_CHAT, 
	module = {	
		@TModule(type=ChatSettingModule.class, 
			name=CHATKey.MODULE_SERVER_SETTINGS, 
			menu=CHATKey.MENU_SERVER, 
			description=CHATKey.MODULE_DESC_SERVER),
		@TModule(type=TChatModule.class, 
			name=CHATKey.MODULE_MESSAGES, 
			menu = CHATKey.MENU_CLIENT,
			description=CHATKey.MODULE_DESC_CLIENT)
	}, packageName = "org.tedros.chat", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"CHAT"})
@TSecurity(id=DomainApp.MNEMONIC, 
	appName = CHATKey.APP_CHAT, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// Run at startup
	}
	
	
}
