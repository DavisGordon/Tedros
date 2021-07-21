/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.solidarity.module.doacao;

import com.solidarity.module.acao.model.SiteDoacaoModelView;
import com.tedros.core.TModule;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;


/**
 * The person crud module
 *
 * @author Davis Gordon
 *
 */
@Deprecated
/*@TSecurity(	id="SOLIDARITY_SITE_MODULE", appName = "#{app.name}", moduleName = "#{module.manage.campaign}", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)*/
public class DoacaoModule extends TModule{

	@Override
	public void tStart() {
		TDynaView<SiteDoacaoModelView> view = new TDynaView<>(this, SiteDoacaoModelView.class);
		tShowView(view);
	}
}
