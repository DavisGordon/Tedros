/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.solidarity.module.voluntario;

import com.solidarity.module.voluntario.model.VoluntarioModelView;
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
@TSecurity(	id="COVSEMFOME_CADVOL_MODULE", appName = "#{app.name}", moduleName = "Gerenciar Campanha", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CadastroVoluntarioModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<VoluntarioModelView> view = new TDynaView<>(this, VoluntarioModelView.class);
		tShowView(view);
	}
}
