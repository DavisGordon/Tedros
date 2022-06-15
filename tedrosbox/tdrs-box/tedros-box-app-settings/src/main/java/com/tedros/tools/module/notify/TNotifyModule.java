/**
 * 
 */
package com.tedros.tools.module.notify;


import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.domain.DomainApp;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.tools.module.notify.model.TNotifyMV;
import com.tedros.tools.util.ToolsKey;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.NOTIFY_MODULE_ID, appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_NOTIFY, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TNotifyModule extends TModule {
	
	
	@Override
	public void tStart() {
		tShowView(new TDynaView<TNotifyMV>(TNotifyMV.class));
	}

}
