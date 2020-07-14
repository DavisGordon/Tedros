/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.covidsemfome.module.voluntario;

import com.covidsemfome.module.voluntario.model.TipoAjudaModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;


/**
 * The voluntario crud module
 *
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_CADTIPOAJUDA_MODULE", appName = "#{app.name}", moduleName = "Gerenciar Campanha", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CadastroTipoAjudaModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<TipoAjudaModelView> view = new TDynaView<>(this, TipoAjudaModelView.class);
		tShowView(view);
	}
}
