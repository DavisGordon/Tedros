/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package org.somos.module.empresaParceira;

import org.somos.module.empresaParceira.model.EmpresaParceiraModelView;
import org.somos.module.empresaParceira.model.ImagemMV;
import org.somos.module.empresaParceira.model.SiteConteudoModelView;

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
@TSecurity(	id="SOMOS_PARCEIRO_MODULE", appName = "#{somos.name}", moduleName = "Administrativo", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EmpresaParceiraModule extends TModule{

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Atualizar Website", 
				new TViewItem(TDynaGroupView.class, EmpresaParceiraModelView.class, "Parceiros"),
				new TViewItem(TDynaGroupView.class, SiteConteudoModelView.class, "Website Conteudo", true),
				new TViewItem(TDynaGroupView.class, ImagemMV.class, "Fotos e Imagens", true)
				));
	}
}
