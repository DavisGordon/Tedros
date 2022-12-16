package org.tedros.fx.presenter.entity.behavior;

import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.fx.presenter.entity.decorator.TSaveViewDecorator;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.entity.ITEntity;

/**
 * The save view behavior. 
 * This behavior can be applied to save an entity. 
 * An entity is created if no entity was defined
 * at view initialization. Only the save button 
 * is configured.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
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
	/**
	 * Initialize the behavior.
	 */
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
	public void colapseAction() {
		
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(M model) {
		return true;
	}
		
}
