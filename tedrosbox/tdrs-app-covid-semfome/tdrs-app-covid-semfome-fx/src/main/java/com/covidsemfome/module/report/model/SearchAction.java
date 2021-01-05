/**
 * 
 */
package com.covidsemfome.module.report.model;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class SearchAction extends TPresenterAction {

	@Override
	public boolean runBefore(ITPresenter presenter) {
		return true;
	}

	@Override
	public void runAfter(ITPresenter presenter) {
		
		TDynaPresenter p = (TDynaPresenter) presenter;
		TDataSetReportBehavior b =  (TDataSetReportBehavior) p.getBehavior();
		TDefaultForm f = (TDefaultForm) ((TProgressIndicatorForm) b.getForm()).gettForm();
		for(Node n : f.getChildren())
			if(n instanceof Accordion) {
				Accordion acc = (Accordion) n;
				TitledPane res = acc.getPanes().get(1);
				acc.setExpandedPane(res);
				break;
			}
		

	}

	

}
