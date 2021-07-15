/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.solidarity.module.pessoa;

import com.solidarity.module.pessoa.model.PessoaModelView;
import com.solidarity.module.pessoa.model.TermoAdesaoModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;


/**
 * The person crud module
 *
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_CADPESS_MODULE", appName = "#{app.name}", moduleName = "Administrativo", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CadastroDePessoaModule extends TModule{

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Pessoa Voluntaria", 
				new TViewItem(TDynaView.class, PessoaModelView.class, "Pessoa"),
				new TViewItem(TDynaView.class, TermoAdesaoModelView.class, "Termo Adesão")
				));
	}
}