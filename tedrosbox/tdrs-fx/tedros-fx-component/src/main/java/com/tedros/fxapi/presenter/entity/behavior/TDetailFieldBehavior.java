package com.tedros.fxapi.presenter.entity.behavior;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDetailFieldBaseBehavior;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
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
	
	@Override
	public void addAction(TPresenterAction action) {
		boolean flag = false;
		if(action!=null && action.getTypes()!=null) { 
			for(TActionType a : new TActionType[] {TActionType.REMOVE, TActionType.CLEAN, TActionType.ADD})
				if(ArrayUtils.contains(action.getTypes(), a)) {
					flag = true;
					break;
				}	
		}else
			flag = true;
		if(flag) {
			super.addAction(action);
		}else {
			final TDynaPresenter presenter = getModulePresenter();
			final TDynaViewSimpleBaseBehavior behavior = (TDynaViewSimpleBaseBehavior) presenter.getBehavior(); 
			behavior.addAction(action);
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
