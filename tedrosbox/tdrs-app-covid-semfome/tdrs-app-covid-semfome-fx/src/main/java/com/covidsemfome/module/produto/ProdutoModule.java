package com.covidsemfome.module.produto;

import com.covidsemfome.module.produto.model.EntradaModelView;
import com.covidsemfome.module.produto.model.ProdutoModelView;
import com.covidsemfome.module.produto.model.SaidaModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

@TSecurity(	id="COVSEMFOME_PRODUTO_MODULE", appName = "#{app.name}", 
moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ProdutoModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Produto", 
				new TViewItem(TDynaView.class, ProdutoModelView.class, "Produtos"),
				new TViewItem(TDynaView.class, EntradaModelView.class, "Entrada"),
				new TViewItem(TDynaView.class, SaidaModelView.class, "Saida")
				));
	}

}
