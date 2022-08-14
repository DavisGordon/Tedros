package org.tedros.fx.presenter.entity.behavior;

import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.fx.presenter.entity.decorator.TSaveViewDecorator;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.entity.ITEntity;

@SuppressWarnings({ "rawtypes" })
public class TSaveViewBehavior<M extends TModelView, E extends ITEntity>
extends TDynaViewCrudBaseBehavior<M, E> {
	
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
		
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(M model) {
		return true;
	}
		
}
