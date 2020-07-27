/**
 * 
 */
package com.covidsemfome.module.report;

import com.covidsemfome.module.report.model.DoacaoReportModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_CADTIPOAJUDA_MODULE", appName = "#{app.name}", moduleName = "Relatorios", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DoacaoReportModule extends TModule {

	@Override
	public void tStart() {
		TDynaView<DoacaoReportModelView> view = new TDynaView<>(this, DoacaoReportModelView.class);
		tShowView(view);
	}

}
