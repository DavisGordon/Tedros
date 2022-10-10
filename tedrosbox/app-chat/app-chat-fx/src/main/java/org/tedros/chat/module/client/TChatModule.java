/**
 * 
 */
package org.tedros.chat.module.client;

import org.tedros.chat.CHATKey;
import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

/**
 * @author Davis Gordon
 *
 */

@TSecurity(	id=DomainApp.CHAT_MODULE_ID, appName=CHATKey.APP_CHAT, moduleName=CHATKey.MODULE_MESSAGES, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TChatModule extends TModule {

	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(this, TChatMV.class));
	}
}
