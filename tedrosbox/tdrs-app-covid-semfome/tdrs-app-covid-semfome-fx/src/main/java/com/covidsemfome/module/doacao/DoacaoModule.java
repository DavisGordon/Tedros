/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.covidsemfome.module.doador;

import com.covidsemfome.module.doador.model.DoadorModelView;
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
@TSecurity(	id="APP_COVIDSEMFOME_DOADOR", appName = "#{app.name}", moduleName = "#{label.donor}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DoadorModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<DoadorModelView> view = new TDynaView<>(this, DoadorModelView.class);
		tShowView(view);
	}
}
