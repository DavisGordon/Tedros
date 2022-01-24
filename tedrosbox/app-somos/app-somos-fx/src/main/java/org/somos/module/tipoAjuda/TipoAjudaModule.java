/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package org.somos.module.tipoAjuda;

import org.somos.module.tipoAjuda.model.TipoAjudaModelView;

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
@TSecurity(	id="SOMOS_CADTIPOAJUDA_MODULE", appName = "#{somos.name}", moduleName = "Gerenciar Campanha", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TipoAjudaModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<TipoAjudaModelView> view = new TDynaView<>(this, TipoAjudaModelView.class);
		tShowView(view);
	}
}
