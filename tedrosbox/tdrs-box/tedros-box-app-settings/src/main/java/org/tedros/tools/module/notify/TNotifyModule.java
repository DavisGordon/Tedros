/**
 * 
 */
package org.tedros.tools.module.notify;


import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.notify.model.TNotify;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.notify.model.TNotifyMV;

/**
 * @author Davis Gordon
 *
 */
@TView(items = {
	@TItem(title=ToolsKey.VIEW_NOTIFY, description=ToolsKey.VIEW_NOTIFY_DESC,
	model = TNotify.class, modelView=TNotifyMV.class)
})
@TSecurity(	id=DomainApp.NOTIFY_MODULE_ID, appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_NOTIFY, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TNotifyModule extends TModule {
	
}
