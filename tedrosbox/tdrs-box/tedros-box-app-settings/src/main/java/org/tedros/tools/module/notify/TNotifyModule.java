/**
 * 
 */
package org.tedros.tools.module.notify;


import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.notify.model.TNotify;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.notify.model.TNotifyMV;

/**
 * @author Davis Gordon
 *
 */
@TLoadable({
	@TModel(modelType = TNotify.class, modelViewType=TNotifyMV.class, moduleType = TNotifyModule.class)
})
@TSecurity(	id=DomainApp.NOTIFY_MODULE_ID, appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_NOTIFY, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TNotifyModule extends TModule {
	
	
	@Override
	public void tStart() {
		tShowView(new TDynaView<TNotifyMV>(this, TNotifyMV.class));
	}

}
