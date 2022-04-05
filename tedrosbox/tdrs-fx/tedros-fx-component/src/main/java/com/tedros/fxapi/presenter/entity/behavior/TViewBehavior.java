package com.tedros.fxapi.presenter.entity.behavior;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings({ "rawtypes" })
public class TViewBehavior<M extends TModelView, E extends ITModel>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior<M, E> {
		
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
