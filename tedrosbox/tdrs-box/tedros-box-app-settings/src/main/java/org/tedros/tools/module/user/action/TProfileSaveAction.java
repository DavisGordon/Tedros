/**
 * 
 */
package org.tedros.tools.module.user.action;

import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.security.model.TProfile;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.exception.TValidatorException;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.module.user.model.TProfileMV;
import org.tedros.tools.module.user.process.TProfileProcess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class TProfileSaveAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public TProfileSaveAction() {
		super(TActionType.SAVE);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean runBefore() {
		
		TDynaPresenter<TProfileMV> p = (TDynaPresenter<TProfileMV>) super.getPresenter();
		TMasterCrudViewBehavior<TProfileMV, TProfile> b = (TMasterCrudViewBehavior<TProfileMV, TProfile>) p.getBehavior();
	
		TProfileMV mv = b.getModelView();
		ObservableList<TProfileMV> l = FXCollections.observableArrayList();
		l.add(mv);
		try {
			b.validateModels(l);
			
			TProfileProcess prc = new TProfileProcess();
			prc.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					List<TResult<TProfile>> lst = prc.getValue();
					if(lst!=null && !lst.isEmpty()) {
						TResult<TProfile> res = lst.get(0);
						TProfile e = res.getValue();
						TLanguage lang = TLanguage.getInstance();
						if(res.getState().equals(TState.SUCCESS)) {
							b.addMessage(new TMessage(lang.getFormatedString(TFxKey.MESSAGE_SAVE, e.getName()), 
									lang.getString(TFxKey.BUTTON_RELOAD), ev->{	
										b.setModelView(null);
										b.loadModels();
										b.getView().tHideModal();
									}));
						}else {
							String msg = res.getState().equals(TState.OUTDATED) 
									? lang.getString(TFxKey.MESSAGE_OUTDATE)
											: res.getMessage();
							if(res.getState().equals(TState.ERROR)) 
								b.addMessage(new TMessage(TMessageType.ERROR, msg));
							else
								b.addMessage(new TMessage(TMessageType.WARNING, msg));
						}
					}
				}
			});
			prc.save(mv.getEntity());
			prc.startProcess();
		} catch (TProcessException e) {
			e.printStackTrace();
			b.getView().tShowModal(new TMessageBox(e), true);
		} catch (TValidatorException e1) {
			b.getView().tShowModal(new TMessageBox(e1), true);
		} catch (Exception e1) {
			e1.printStackTrace();
			b.getView().tShowModal(new TMessageBox(e1), true);
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}
