package org.tedros.chat.start;

import org.tedros.chat.CHATKey;
import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.module.client.TChatModule;
import org.tedros.chat.module.client.setting.ChatClient;
import org.tedros.chat.module.server.ChatSettingModule;
import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;

import javafx.beans.value.ChangeListener;

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

	private ChangeListener<String> chl = (a,o,n)->{
		TedrosContext.pushInfo(new TMessage(TMessageType.ERROR, n));
	};
	@Override
	public void start() {
		ChatClient c = ChatClient.getInstance();
		c.logProperty().addListener(chl);
		c.connect();
	}

	@Override
	public void stop() {
		ChatClient c = ChatClient.getInstance();
		c.logProperty().removeListener(chl);
		c.close();
	}
}
