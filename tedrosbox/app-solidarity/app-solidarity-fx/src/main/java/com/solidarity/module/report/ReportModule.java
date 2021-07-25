/**
 * 
 */
package com.solidarity.module.report;

import com.solidarity.module.report.model.AcoesReportModelView;
import com.solidarity.module.report.model.EstoqueReportModelView;
import com.solidarity.module.report.model.VoluntariosReportModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOLIDARITY_REPORT_MODULE", appName = "#{app.name}", moduleName = "#{module.administrativo}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ReportModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.relatorios}", 
				new TViewItem(TDynaView.class, AcoesReportModelView.class, "#{view.campaign}"),
				new TViewItem(TDynaView.class, VoluntariosReportModelView.class, "#{view.pessoa}"),
				new TViewItem(TDynaView.class, EstoqueReportModelView.class, "#{view.estoque}")
				));
	}

}
