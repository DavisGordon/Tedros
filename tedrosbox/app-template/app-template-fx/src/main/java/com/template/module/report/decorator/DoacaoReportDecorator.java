/**
 * 
 */
package com.template.module.report.decorator;

import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewReportBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;

/**
 * @author Davis Gordon
 *
 */
public class DoacaoReportDecorator<M extends TModelView> extends TDynaViewReportBaseDecorator<M> {

	@SuppressWarnings("unchecked")
	public void decorate() {
		
		// get the model view annotation array 
		final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
		
		// get the view
		final ITDynaView<M> view = getPresenter().getView();
		
		addItemInTCenterContent(view.gettFormSpace());
		setViewTitle(null);
		
		final TForm tForm = this.getPresenter().getFormAnnotation();
		
		//buildColapseButton(null);
		buildCancelButton(null);
		buildSearchButton(null);
		buildExcelButton(null);
		buildWordButton(null);
		buildPdfButton(null);
		buildCleanButton(null);
		buildModesRadioButton(null, null);
		
		
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettSearchButton(), gettCleanButton(), gettExcelButton(), gettWordButton(), gettPdfButton(), gettCancelButton());
		
		// add the mode radio buttons
		addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
		
	}
	

}
