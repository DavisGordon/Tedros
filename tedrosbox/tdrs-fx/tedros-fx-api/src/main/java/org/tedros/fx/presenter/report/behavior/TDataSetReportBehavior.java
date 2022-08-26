package org.tedros.fx.presenter.report.behavior;

import java.lang.reflect.InvocationTargetException;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewReportBaseBehavior;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.model.ITReportModel;

@SuppressWarnings({ "rawtypes" })
public class TDataSetReportBehavior<M extends TModelView, E extends ITReportModel>
extends TDynaViewReportBaseBehavior<M, E> {
	
		
	@Override
	public void load() {
		super.load();
		initialize();
	}
		
	public void initialize() {
		configCleanButton();
		configSearchButton();
		configPdfButton();
		configExcelButton();
		configOpenExportFolderButton();
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
