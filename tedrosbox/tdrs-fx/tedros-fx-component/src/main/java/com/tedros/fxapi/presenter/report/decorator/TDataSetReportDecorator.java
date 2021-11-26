package com.tedros.fxapi.presenter.report.decorator;

import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewReportBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public class TDataSetReportDecorator<M extends TModelView> 
extends TDynaViewReportBaseDecorator<M> {
	
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
