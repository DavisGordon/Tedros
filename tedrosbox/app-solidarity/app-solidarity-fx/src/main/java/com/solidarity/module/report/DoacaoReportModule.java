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
@TSecurity(	id="COVSEMFOME_REPORT_MODULE", appName = "#{app.name}", moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DoacaoReportModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Relatórios", 
				new TViewItem(TDynaView.class, AcoesReportModelView.class, "Ação"),
				new TViewItem(TDynaView.class, VoluntariosReportModelView.class, "Voluntário"),
				new TViewItem(TDynaView.class, EstoqueReportModelView.class, "Estoque")
				));
	}

}
