/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.covidsemfome.module.voluntario;

import com.covidsemfome.module.voluntario.model.VoluntarioModelView;
import com.tedros.core.TModule;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;


/**
 * The voluntario crud module
 *
 * @author Davis Gordon
 *
 */
/*@TSecurity(	id="COVSEMFOME_CADVOL_MODULE", appName = "#{app.name}", moduleName = "Gerenciar Campanha", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)*/
public class CadastroVoluntarioModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<VoluntarioModelView> view = new TDynaView<>(this, VoluntarioModelView.class);
		tShowView(view);
	}
}
