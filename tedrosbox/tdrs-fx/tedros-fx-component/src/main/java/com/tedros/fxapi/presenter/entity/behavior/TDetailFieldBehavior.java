package com.tedros.fxapi.presenter.entity.behavior;

import java.lang.reflect.InvocationTargetException;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.behavior.TDetailFieldBaseBehavior;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings({ "rawtypes" })
public class TDetailFieldBehavior<M extends TEntityModelView, E extends ITEntity>
extends TDetailFieldBaseBehavior<M, E> {
	
	public void load() {
		super.load();
		initialize();
	}
	
	public void initialize() {
		super.configAddButton();
		super.configCleanButton();
		super.configRemoveButton();
		
		try {
			TModelView model = (M) super.getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance());
			setModelView(model);
			super.showForm(TViewMode.EDIT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	


	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettAddButton()!=null)
			decorator.gettAddButton().setDisable(flag);
		if(decorator.gettRemoveButton()!=null)
			decorator.gettRemoveButton().setDisable(flag);
		if(decorator.gettCleanButton()!=null)
			decorator.gettCleanButton().setDisable(flag);
	}


	@Override
	public String canInvalidate() {
		return null;
	}
}
