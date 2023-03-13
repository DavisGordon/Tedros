/**
 * 
 */
package org.tedros.tools.module.ai;

import org.tedros.core.TModule;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.model.AiChatMV;
import org.tedros.tools.module.ai.model.CompletionMV;

/**
 * @author Davis Gordon
 *
 */
public class TAiModule extends TModule {

	
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, ToolsKey.MODULE_AI,
				new TViewItem(TDynaGroupView.class, CompletionMV.class, ToolsKey.VIEW_AI_COMPLETION),
				new TViewItem(TDynaGroupView.class, AiChatMV.class, ToolsKey.VIEW_AI_CHAT)));
	}
}
