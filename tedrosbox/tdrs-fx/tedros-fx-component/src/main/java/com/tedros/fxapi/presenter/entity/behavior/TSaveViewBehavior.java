package com.tedros.fxapi.presenter.entity.behavior;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TModelViewUtil;

@SuppressWarnings({ "rawtypes" })
public class TSaveViewBehavior<M extends TModelView, E extends ITEntity>
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
			
			if(super.getModels()!=null && !super.getModels().isEmpty()) {
				M model = (M) super.getModels().get(0);
				setModelView(model);
			}else {
				super.newAction();
			}
			
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
