/**
 * 
 */
package com.tedros.settings.properties.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.ejb.controller.TOwnerController;
import com.tedros.core.owner.model.TOwner;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.fxapi.process.TEntityProcess;
import com.tedros.settings.properties.model.TOwnerMV;

import javafx.concurrent.Worker.State;

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean runBefore() {
		
		TSaveViewBehavior bv = (TSaveViewBehavior) ((TDynaPresenter)super.getPresenter()).getBehavior();
		
		TEntityProcess<TOwner> p = new TEntityProcess(TOwner.class, TOwnerController.JNDI_NAME) {};
		p.stateProperty().addListener((a,o,n)->{
			if(n.equals(State.SUCCEEDED)) {
				List l = p.getValue();
				if(l!=null && !l.isEmpty()) {
					TResult r = (TResult) l.get(0);
					if(r.getState().equals(TState.SUCCESS)) {
						List l2 = (List) r.getValue();
						if(l2!=null && !l2.isEmpty()) {
							TOwner e = (TOwner) l2.get(0);
							bv.setModelView(new TOwnerMV(e));
						}else
							bv.setModelView(new TOwnerMV());
					}else {
						String msg = r.getMessage();
						if(StringUtils.isBlank(msg))
							msg = TLanguage.getInstance().getString("#{tedros.fxapi.message.error}");

						bv.addMessage(new TMessage(TMessageType.ERROR, msg));
					}
				}else
					bv.setModelView(new TOwnerMV());
			}
		});
		bv.getView().gettProgressIndicator().bind(p.runningProperty());
		p.list();
		p.startProcess();
		/*try {
			TOwner e = new TOwnerProperties().getOwner();
			if(e!=null) {
				bv.setModelView(new TOwnerMV(e));
			}else
				bv.setModelView(new TOwnerMV());
			
		} catch (TException e) {
			e.printStackTrace();
			bv.addMessage(new TMessage(TMessageType.ERROR, e.getMessage()));
		}*/
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
