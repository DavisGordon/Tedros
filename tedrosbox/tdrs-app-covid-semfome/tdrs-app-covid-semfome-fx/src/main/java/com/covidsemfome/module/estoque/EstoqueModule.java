/**
 * 
 */
package com.covidsemfome.module.estoque;

import com.covidsemfome.module.estoque.model.EstoqueConfigModelView;
import com.covidsemfome.module.estoque.model.EstoqueModelView;
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
@TSecurity(	id="COVSEMFOME_ESTOQUE_MODULE", appName = "#{app.name}", moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EstoqueModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		TDynaView<EstoqueModelView> view = new TDynaView<>(this, EstoqueModelView.class);
		tShowView(new TGroupView<TGroupPresenter>(this, "Estoque", 
				new TViewItem(TDynaView.class, EstoqueModelView.class, "Ver Estoque"),
				new TViewItem(TDynaView.class, EstoqueConfigModelView.class, "Editar Estoque Inicial")
				));
	}

}
