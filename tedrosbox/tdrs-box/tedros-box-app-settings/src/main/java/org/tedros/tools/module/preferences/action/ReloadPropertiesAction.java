/**
 * 
 */
package org.tedros.tools.module.preferences.action;

import org.tedros.core.context.TedrosContext;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;

/**
 * @author Davis Gordon
 *
 */
public class ReloadPropertiesAction extends TPresenterAction {

	public ReloadPropertiesAction() {
		super(TActionType.SAVE, TActionType.DELETE);
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
		TedrosContext.loadCustomProperties();

	}

}
