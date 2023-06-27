/**
 * 
 */
package org.tedros.tools.module.preferences;


import org.tedros.common.model.TMimeType;
import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.preferences.model.TMimeTypeMV;
import org.tedros.tools.module.preferences.model.TPropertieMV;

/**
 * @author Davis Gordon
 *
 */
@TView(title=ToolsKey.VIEW_SYSTEM_PROPERTIES,
items = {
	@TItem(title=ToolsKey.VIEW_MAIN_PROPERTIES, description=ToolsKey.VIEW__MAIN_PROPERTIES_DESC,
	model = TPropertie.class, modelView=TPropertieMV.class),
	@TItem(title=ToolsKey.VIEW_MIMETYPE, description=ToolsKey.VIEW_MIMETYPE_DESC,
	model = TMimeType.class, modelView=TMimeTypeMV.class)
})
@TSecurity(id=DomainApp.SETTINGS_MODULE_ID, appName=ToolsKey.APP_TOOLS, 
moduleName=ToolsKey.MODULE_PREFERENCES, allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TSettingsModule extends TModule {
	
}
