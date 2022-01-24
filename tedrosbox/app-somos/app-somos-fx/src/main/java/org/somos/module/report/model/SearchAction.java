/**
 * 
 */
package org.somos.module.report.model;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.presenter.behavior.TActionType;
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
	
	/**
	 * @param tActionType
	 */
	public SearchAction() {
		super(TActionType.SEARCH);
	}
	
	@Override
	public boolean runBefore() {
		return true;
	}

	@Override
	public void runAfter() {
		TDynaPresenter p = super.getPresenter();
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
