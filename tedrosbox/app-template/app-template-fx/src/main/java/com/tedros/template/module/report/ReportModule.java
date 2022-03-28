/**
 * 
 */
package com.tedros.template.module.report;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.template.module.report.model.ProdutoReportModelView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOMOS_REPORT_MODULE", appName = "#{myapp.name}", moduleName = "#{module.adm}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ReportModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "#{label.reports}", 
				new TViewItem(TDynaGroupView.class, ProdutoReportModelView.class, "#{label.product}")
				));
	}

}
