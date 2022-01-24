/**
 * 
 */
package org.somos.module.estoque;

import org.somos.module.estoque.model.EstoqueConfigModelView;
import org.somos.module.estoque.model.EstoqueModelView;
import org.somos.module.produto.model.EntradaModelView;
import org.somos.module.produto.model.ProdutoModelView;
import org.somos.module.produto.model.SaidaModelView;

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
@TSecurity(	id="SOMOS_ESTOQUE_MODULE", appName = "#{somos.name}", moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EstoqueModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Estoque", 
				new TViewItem(TDynaGroupView.class, ProdutoModelView.class, "Produtos"),
				new TViewItem(TDynaGroupView.class, EstoqueConfigModelView.class, "Editar Estoque Inicial"),
				new TViewItem(TDynaGroupView.class, EntradaModelView.class, "Entradas de produtos"),
				new TViewItem(TDynaGroupView.class, SaidaModelView.class, "Saídas de produtos"),
				new TViewItem(TDynaGroupView.class, EstoqueModelView.class, "Ver Estoque gerado")
				));
	}

}
