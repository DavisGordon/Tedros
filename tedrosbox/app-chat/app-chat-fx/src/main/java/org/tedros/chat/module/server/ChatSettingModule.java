/**
 * 
 */
package org.tedros.chat.module.server;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.chat.CHATKey;
import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.module.server.model.ChatSettingMV;

/**
 * @author Davis Dun
 *
 */
@TSecurity(id=DomainApp.CHAT_SETTING_MODULE_ID, 
appName = CHATKey.APP_CHAT, 
moduleName = CHATKey.MODULE_SERVER_SETTINGS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ChatSettingModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(this, ChatSettingMV.class));

	}

}
