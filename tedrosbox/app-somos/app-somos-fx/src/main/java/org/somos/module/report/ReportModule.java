/**
 * 
 */
package org.somos.module.report;

import org.somos.module.report.model.ProdutoReportModelView;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOMOS_REPORT_MODULE", appName = "#{somos.name}", moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ReportModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Relatórios", 
				new TViewItem(TDynaGroupView.class, ProdutoReportModelView.class, "Produto")
				));
	}

}
