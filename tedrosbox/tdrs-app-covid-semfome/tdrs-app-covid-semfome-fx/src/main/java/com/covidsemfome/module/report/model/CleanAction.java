/**
 * 
 */
package com.covidsemfome.module.report.model;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 * @author Davis Gordon
 *
 */
public class CleanAction extends TPresenterAction {

	@Override
	public boolean runBefore(ITPresenter presenter) {
		return true;
	}

	@Override
	public void runAfter(ITPresenter presenter) {
		
		TDynaPresenter p = (TDynaPresenter) presenter;
		TDataSetReportBehavior b =  (TDataSetReportBehavior) p.getBehavior();
		//DoacaoReportModelView m = (DoacaoReportModelView) b.getModelView();
		TDefaultForm f = (TDefaultForm) b.getForm();
		Accordion acc = (Accordion) f.getChildren().get(0);
		TitledPane fil = acc.getPanes().get(0);
		acc.setExpandedPane(fil);
		

	}

	

}
