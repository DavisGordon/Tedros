/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.covidsemfome.module.pessoa;

import com.covidsemfome.module.pessoa.model.PessoaModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;


/**
 * The person crud module
 *
 * @author Davis Gordon
 *
 */
@TSecurity(	id="T_APP_GLOBAL_BRASIL_CAD_PESSOA", appName = "#{app.name}", moduleName = "#{label.person}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CadastroDePessoaModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<PessoaModelView> view = new TDynaView<>(this, PessoaModelView.class);
		tShowView(view);
	}
}
