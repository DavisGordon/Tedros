package org.tedros.fx.presenter.report.decorator;

import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewReportBaseDecorator;

/**
 * The decorator of the report view.
 * @author Davis Gordon
 *
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public class TDataSetReportDecorator<M extends TModelView> 
extends TDynaViewReportBaseDecorator<M> {
	
	@Override
	@SuppressWarnings("unchecked")
	 public void decorate() {
		
		// get the view
		final ITDynaView<M> view = getPresenter().getView();
		
		addItemInTCenterContent(view.gettFormSpace());
		setViewTitle(null);
		
		buildCleanButton(null);
		buildPdfButton(null);
		buildExcelButton(null);
		buildSearchButton(null);
		super.buildOpenExportFolderButton(null);
		buildModesRadioButton(null, null);
		
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettOpenExportFolderButton(), gettSearchButton(), gettCleanButton(), gettPdfButton(), gettExcelButton());
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
	}
}
