package com.tedros.fxapi.presenter.modal.behavior;

import java.lang.reflect.InvocationTargetException;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewSelectionBaseBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TSelectionModalDecorator;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings({ "rawtypes" })
public class TSelectionModalBehavior<M extends TModelView, E extends ITEntity>
extends TDynaViewSelectionBaseBehavior<M, E> {
	
	private TSelectionModalDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TSelectionModalDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
		
	public void initialize() {
		configCleanButton();
		configSearchButton();
		configCancelButton();
		configCloseButton();
		configAddButton();
		configSelectAllButton();
		configSelectedListView();
		final Class<E> entityClass = getModelClass();
		
		addAction(new TPresenterAction(TActionType.SEARCH) {
			@Override
			public boolean runBefore() {
				return true;
			}

			@Override
			public void runAfter() {
				decorator.expandResultPane();
			}
			
		});
		
		addAction(new TPresenterAction(TActionType.CLEAN, TActionType.CANCEL) {
			@Override
			public boolean runBefore() {
				return true;
			}

			@Override
			public void runAfter() {
				decorator.expandFilterPane();
			}
			
		});
		
		M model;
		try {
			model = (M) getModelViewClass().getConstructor(entityClass).newInstance(entityClass.newInstance());
			setModelView(model);
			showForm(TViewMode.EDIT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public String canInvalidate() {
		return null;
	}
		
}
