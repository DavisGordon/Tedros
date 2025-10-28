package org.tedros.fx.presenter.report.behavior;

import java.lang.reflect.InvocationTargetException;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewReportBaseBehavior;
import org.tedros.server.model.ITReportModel;

/**
 * The behavior of the report view.
 * For report processing use 
 * {@link org.tedros.fx.annotation.process.TReportProcess} 
 * in the TEntityModelView.
 * @author Davis Gordon
 *
 * @param <M>
 * @param <E>
 */
@SuppressWarnings({ "rawtypes" })
public class TDataSetReportBehavior<M extends TModelView<E>, E extends ITReportModel>
extends TDynaViewReportBaseBehavior<M, E> {
	
		
	@Override
	public void load() {
		super.load();
		initialize();
	}
	/**
	 * Initialize the behavior
	 */
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
			model = (M) getModelViewClass().getConstructor(entityClass).newInstance(entityClass.getDeclaredConstructor().newInstance());
			setModelView(model);
			showForm(TViewMode.EDIT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOGGER.error(e.getMessage(), e);
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
