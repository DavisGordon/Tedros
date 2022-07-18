/**
 * 
 */
package com.tedros.tools.module.preferences.action;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;

/**
 * @author Davis Gordon
 *
 */
public class ReloadPropertiesAction extends TPresenterAction {

	public ReloadPropertiesAction() {
		super(TActionType.SAVE, TActionType.DELETE);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runBefore()
	 */
	@Override
	public boolean runBefore() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		TedrosContext.loadCustomProperties();

	}

}
