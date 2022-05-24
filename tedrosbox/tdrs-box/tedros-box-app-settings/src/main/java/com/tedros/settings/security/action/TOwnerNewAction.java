/**
 * 
 */
package com.tedros.settings.security.action;

import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.ejb.controller.TOwnerController;
import com.tedros.core.owner.model.TOwner;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.settings.security.model.TOwnerMV;

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
	@Override
	public boolean runBefore() {
		
		TSaveViewBehavior bv = (TSaveViewBehavior) ((TDynaPresenter)super.getPresenter()).getBehavior();
					
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TOwnerController serv = loc.lookup(TOwnerController.JNDI_NAME);
			TResult res = serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), TOwner.class);
			if(res.getResult().equals(EnumResult.SUCESS)) {
				List<TOwner> l = (List<TOwner>) res.getValue();
				if(l!=null && !l.isEmpty()) {
					TOwner e = l.get(0);
					bv.setModelView(new TOwnerMV(e));
				}else
					bv.setModelView(new TOwnerMV());
			}else {
				String msg = res.getMessage();
				if(StringUtils.isBlank(msg))
					msg = TLanguage.getInstance().getString("#{tedros.fxapi.message.error}");
				bv.addMessage(new TMessage(TMessageType.ERROR, msg));
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			bv.addMessage(new TMessage(TMessageType.ERROR, e.getMessage()));
		}finally{
			loc.close();
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
