/**
 * 
 */
package org.tedros.tools.module.ai;

import org.tedros.core.TModule;
import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.model.AiChatMV;
import org.tedros.tools.module.ai.model.CompletionMV;
import org.tedros.tools.module.ai.model.CreateImageMV;

/**
 * @author Davis Gordon
 *
 */
@TView(title=ToolsKey.MODULE_AI,
items = {
	@TItem(title=ToolsKey.VIEW_AI_CREATE_IMAGE, description=ToolsKey.VIEW_AI_CREATE_IMAGE_DESC,
	model = TAiCreateImage.class, modelView=CreateImageMV.class, groupHeaders=true),
	@TItem(title=ToolsKey.VIEW_AI_COMPLETION, description=ToolsKey.VIEW_AI_COMPLETION_DESC,
	model = TAiCompletion.class, modelView=CompletionMV.class),
	@TItem(title=ToolsKey.VIEW_AI_CHAT, description=ToolsKey.VIEW_AI_CHAT_DESC,
	model = TAiChatCompletion.class, modelView=AiChatMV.class)
})
@TSecurity(	id=DomainApp.TEROS_MODULE_ID, appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TAiModule extends TModule {
	
}
