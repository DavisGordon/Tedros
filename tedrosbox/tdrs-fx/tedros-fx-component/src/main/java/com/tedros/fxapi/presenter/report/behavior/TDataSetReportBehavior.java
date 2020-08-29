package com.tedros.fxapi.presenter.report.behavior;

import java.lang.reflect.InvocationTargetException;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.ejb.base.model.ITReportModel;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;

@SuppressWarnings({ "rawtypes" })
public class TDataSetReportBehavior<M extends TModelView, E extends ITReportModel>
extends com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewReportBaseBehavior<M, E> {
	
	private TDataSetReportDecorator<M> decorator;
		
	@Override
	public void load() {
		super.load();
		this.decorator = (TDataSetReportDecorator<M>) getPresenter().getDecorator();
		initialize();
	}
		
	@SuppressWarnings("unchecked")
	public void initialize() {
		configCleanButton();
		configSearchButton();
		configPdfButton();
		configExcelButton();
		setDisableModelActionButtons(true);
		final Class<E> entityClass = getModelClass();
		
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
	public void colapseAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String canInvalidate() {
		return null;
	}
		
}
