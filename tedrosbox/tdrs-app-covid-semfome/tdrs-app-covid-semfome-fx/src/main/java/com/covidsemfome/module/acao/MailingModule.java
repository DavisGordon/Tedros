/**
 * 
 */
package com.covidsemfome.module.acao;

import com.covidsemfome.module.acao.model.AcaoVoluntarioModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_ACAOVOL_MODULE", appName = "#{app.name}", moduleName = "Voluntários Inscritos", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class AcaoVoluntarioModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		TDynaView<AcaoVoluntarioModelView> view = new TDynaView<>(this, AcaoVoluntarioModelView.class);
		tShowView(view);
	}

}
