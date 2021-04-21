package com.tedros.fxapi.presenter.entity.behavior;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

@SuppressWarnings({ "rawtypes" })
public class TSaveViewBehavior<M extends TEntityModelView, E extends ITEntity>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior<M, E> {
	
	protected TSaveViewDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TSaveViewDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
		
	public void initialize() {
		try{
			
			configSaveButton();
			configModesRadio();
			
			M model = (M) super.getModels().get(0);
			setModelView(model);
			showForm(TViewMode.EDIT);
			
			
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
	public void colapseAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(M model) {
		// TODO Auto-generated method stub
		return false;
	}
		
}
