/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package com.covidsemfome.module.empresaParceira;

import com.covidsemfome.module.empresaParceira.model.EmpresaParceiraModelView;
import com.covidsemfome.module.empresaParceira.model.SiteConteudoModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;


/**
 * The person crud module
 *
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_PARCEIRO_MODULE", appName = "#{app.name}", moduleName = "Administrativo", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EmpresaParceiraModule extends TModule{

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Atualizar Website", 
				new TViewItem(TDynaGroupView.class, EmpresaParceiraModelView.class, "Parceiros"),
				new TViewItem(TDynaGroupView.class, SiteConteudoModelView.class, "Website Conteudo")
				));
	}
}
