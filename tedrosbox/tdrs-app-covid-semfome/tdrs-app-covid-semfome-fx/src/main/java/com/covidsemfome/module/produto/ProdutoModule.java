package com.covidsemfome.module.produto;

import com.covidsemfome.module.produto.model.ProdutoModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

@TSecurity(	id="COVSEMFOME_PRODUTO_MODULE", appName = "#{app.name}", 
moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ProdutoModule extends TModule {

	@Override
	public void tStart() {
		TDynaView<ProdutoModelView> view = new TDynaView<>(this, ProdutoModelView.class);
		tShowView(view);
	}

}
