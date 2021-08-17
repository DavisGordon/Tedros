/**
 * 
 */
package com.solidarity.module.estoque;

import com.solidarity.module.estoque.model.EstoqueConfigModelView;
import com.solidarity.module.estoque.model.EstoqueModelView;
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

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOLIDARITY_ESTOQUE_MODULE", appName = "#{app.name}", moduleName = "#{module.administrativo}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EstoqueModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.estoque}", 
				new TViewItem(TDynaView.class, ProdutoModelView.class, "#{label.produtos}"),
				new TViewItem(TDynaView.class, EntradaModelView.class, "#{label.entradas}"),
				new TViewItem(TDynaView.class, SaidaModelView.class, "#{label.saidas}"),
				new TViewItem(TDynaView.class, EstoqueModelView.class, "#{view.estoque.title}"),
				new TViewItem(TDynaView.class, EstoqueConfigModelView.class, "#{view.estoque.inicial.title}")
				));
	}

}
