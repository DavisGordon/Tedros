package com.covidsemfome.module.produto;

import com.covidsemfome.module.produto.model.EntradaModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

@TSecurity(	id="COVSEMFOME_ENTRADAPROD_MODULE", appName = "#{app.name}", 
moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EntradaProdutoModule extends TModule {

	@Override
	public void tStart() {
		TDynaView<EntradaModelView> view = new TDynaView<>(this, EntradaModelView.class);
		tShowView(view);
	}

}
