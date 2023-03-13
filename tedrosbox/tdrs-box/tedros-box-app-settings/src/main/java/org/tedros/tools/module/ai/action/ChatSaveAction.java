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
public class ChatSaveAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public ChatSaveAction() {
		super(TActionType.SAVE);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@Override
	public boolean runBefore() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		TDynaPresenter<AiChatMV> p = super.getPresenter();
		AiChatMV mv = p.getBehavior().getModelView();
		AiChatUtil u = new AiChatUtil();
		TAccessToken token = TedrosContext.getLoggedUser().getAccessToken();
		mv.getMessages().forEach(c->{
			TAiChatMessage msg = c.getEntity();
			try {
				msg.setChat(mv.getEntity());
				TAiChatMessage s = u.saveMessage(token, msg);
				c.reload(s);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

	}

}
