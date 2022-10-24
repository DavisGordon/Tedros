/**
 * 
 */
package org.tedros.chat.module.client.action;

import java.util.UUID;

import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;

/**
 * @author Davis Gordon
 *
 */
public class BuildChatCodeAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public BuildChatCodeAction() {
		super(TActionType.NEW);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@Override
	public boolean runBefore() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		TDynaPresenter<TChatMV> p = super.getPresenter();
		p.getBehavior().getModelView().getModel().setCode(UUID.randomUUID().toString());
	}

}
