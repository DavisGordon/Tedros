/**
 * 
 */
package org.tedros.tools.module.user;


import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.core.security.model.TProfile;
import org.tedros.core.security.model.TUser;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.model.TAuthorizationMV;
import org.tedros.tools.module.user.model.TProfileMV;
import org.tedros.tools.module.user.model.TUserMV;

/**
 * @author Davis Gordon
 *
 */
@TView(title=ToolsKey.VIEW_USER_SETTINGS,
items = {
	@TItem(title=ToolsKey.VIEW_AUTHORIZATION, description=ToolsKey.VIEW_AUTHORIZATION_DESC,
	model = TAuthorization.class, modelView=TAuthorizationMV.class, groupHeaders=true),
	@TItem(title=ToolsKey.VIEW_PROFILE, description=ToolsKey.VIEW_PROFILE_DESC,
	model = TProfile.class, modelView=TProfileMV.class),
	@TItem(title=ToolsKey.VIEW_USER, description=ToolsKey.VIEW_USER_DESC,
	model = TUser.class, modelView=TUserMV.class)
})
@TSecurity(	id=DomainApp.USER_MODULE_ID, appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_USER, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TUserModule extends TModule {
	
}
