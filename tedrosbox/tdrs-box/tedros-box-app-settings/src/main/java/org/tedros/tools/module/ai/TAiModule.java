/**
 * 
 */
package org.tedros.tools.module.ai;

import org.tedros.core.TModule;
import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.domain.DomainApp;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.model.AiChatMV;
import org.tedros.tools.module.ai.model.CompletionMV;
import org.tedros.tools.module.ai.model.CreateImageMV;

/**
 * @author Davis Gordon
 *
 */
@TLoadable({
	@TModel(modelType = TAiCreateImage.class, modelViewType=CreateImageMV.class, moduleType = TAiModule.class),
	@TModel(modelType = TAiCompletion.class, modelViewType=CompletionMV.class, moduleType = TAiModule.class),
	@TModel(modelType = TAiChatCompletion.class, modelViewType=AiChatMV.class, moduleType = TAiModule.class)
})
@TSecurity(	id=DomainApp.TEROS_MODULE_ID, appName=ToolsKey.APP_TOOLS, moduleName=ToolsKey.MODULE_AI, 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TAiModule extends TModule {
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, ToolsKey.MODULE_AI,
				new TViewItem(TDynaGroupView.class, CreateImageMV.class, ToolsKey.VIEW_AI_CREATE_IMAGE, true),
				new TViewItem(TDynaGroupView.class, CompletionMV.class, ToolsKey.VIEW_AI_COMPLETION),
				new TViewItem(TDynaGroupView.class, AiChatMV.class, ToolsKey.VIEW_AI_CHAT)));
	}
}
