/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.solidarity.module.voluntario;

import com.solidarity.module.voluntario.model.TipoAjudaModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;


/**
 * Tipos de Ajuda module
 *
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOLIDARITY_CADTIPOAJUDA_MODULE", appName = "#{app.name}", moduleName = "#{module.manage.campaign}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CadastroTipoAjudaModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<TipoAjudaModelView> view = new TDynaView<>(this, TipoAjudaModelView.class);
		tShowView(view);
	}
}
