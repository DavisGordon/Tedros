/**
 * 
 */
package org.tedros.tools.module.ai.action;

import org.tedros.core.ai.model.TAiChatMessage;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.server.security.TAccessToken;
import org.tedros.tools.module.ai.model.AiChatMV;
import org.tedros.tools.module.ai.settings.AiChatUtil;

/**
 * @author Davis Gordon
 *
 */
public class ChatDeleteAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public ChatDeleteAction() {
		super(TActionType.DELETE);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@Override
	public boolean runBefore() {
		TDynaPresenter<AiChatMV> p = super.getPresenter();
		AiChatMV mv = p.getBehavior().getModelView();
		AiChatUtil u = new AiChatUtil();
		TAccessToken token = TedrosContext.getLoggedUser().getAccessToken();
		mv.getMessages().forEach(c->{
			TAiChatMessage msg = c.getEntity();
			try {
				u.deleteMessage(token, msg);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		return true;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		

	}

}
