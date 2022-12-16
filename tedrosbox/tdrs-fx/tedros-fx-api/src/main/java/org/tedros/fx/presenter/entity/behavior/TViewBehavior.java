package org.tedros.fx.presenter.entity.behavior;

import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.model.ITModel;

/**
 * The behavior of the simple view.
 * No buttons, no actions, just show the 
 * first model  on the list defined on the
 * view initialization.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings({ "rawtypes" })
public class TViewBehavior<M extends TModelView, E extends ITModel>
extends TDynaViewSimpleBaseBehavior<M, E> {
		
	@Override
	public void load() {
		super.load();
		initialize();
	}
		
	public void initialize() {
		try{
			M model = (M) super.getModels().get(0);
			setModelView(model);
		}catch(Throwable e){
			getView().tShowModal(new TMessageBox(e), true);
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean invalidate() {
		return super.invalidate();
	}

	@Override
	public String canInvalidate() {
		return null;
	}
}
