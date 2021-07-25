package com.solidarity.module.produto;

import com.solidarity.module.produto.model.EntradaModelView;
import com.solidarity.module.produto.model.ProdutoModelView;
import com.solidarity.module.produto.model.SaidaModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

@TSecurity(	id="SOLIDARITY_PRODUTO_MODULE", appName = "#{app.name}", 
moduleName = "#{module.administrativo}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ProdutoModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.ger.produtos}", 
				new TViewItem(TDynaView.class, ProdutoModelView.class, "#{label.produtos}"),
				new TViewItem(TDynaView.class, EntradaModelView.class, "#{label.entradas}"),
				new TViewItem(TDynaView.class, SaidaModelView.class, "#{label.saidas}")
				));
	}

}
