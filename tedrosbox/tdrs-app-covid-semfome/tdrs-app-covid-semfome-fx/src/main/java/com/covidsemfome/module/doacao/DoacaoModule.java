/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.covidsemfome.module.doacao;

import com.covidsemfome.module.doacao.model.DoacaoModelView;
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
@TSecurity(	id="COVSEMFOME_DOACAO_MODULE", appName = "#{app.name}", moduleName = "Gerenciar Campanha", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DoacaoModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<DoacaoModelView> view = new TDynaView<>(this, DoacaoModelView.class);
		tShowView(view);
	}
}
