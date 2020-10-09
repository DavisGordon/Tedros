/**
 * 
 */
package com.covidsemfome.module.estoque;

import com.covidsemfome.module.estoque.model.EstoqueConfigModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_ESTOQUECONF_MODULE", appName = "#{app.name}", moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EstoqueConfigModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		TDynaView<EstoqueConfigModelView> view = new TDynaView<>(this, EstoqueConfigModelView.class);
		tShowView(view);
	}

}