/**
 * 
 */
package com.tedros.settings.security.action;

import com.tedros.core.owner.model.TOwner;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.settings.security.model.TOwnerMV;
import com.tedros.settings.util.TSettingsUtil;

/**
 * @author Davis Gordon
 *
 */
public class TOwnerNewAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public TOwnerNewAction() {
		super(TActionType.NEW);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runBefore()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean runBefore() {
		
		TSaveViewBehavior bv = (TSaveViewBehavior) ((TDynaPresenter)super.getPresenter()).getBehavior();
		try {
			TOwner e = new TSettingsUtil().getOwner();
			if(e!=null) {
				bv.setModelView(new TOwnerMV(e));
			}else
				bv.setModelView(new TOwnerMV());
			
		} catch (TException e) {
			e.printStackTrace();
			bv.addMessage(new TMessage(TMessageType.ERROR, e.getMessage()));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}
