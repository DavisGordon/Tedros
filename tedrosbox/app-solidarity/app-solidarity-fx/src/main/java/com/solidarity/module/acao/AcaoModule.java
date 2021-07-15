/**
 * 
 */
package com.solidarity.module.acao;

import com.solidarity.module.acao.model.AcaoModelView;
import com.solidarity.module.acao.model.SiteAboutModelView;
import com.solidarity.module.acao.model.SiteContatoModelView;
import com.solidarity.module.acao.model.SiteDoacaoModelView;
import com.solidarity.module.acao.model.SiteNoticiaModelView;
import com.solidarity.module.acao.model.SiteTermoModelView;
import com.solidarity.module.acao.model.SiteVideoModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_ACAOSITE_MODULE", appName = "#{app.name}", moduleName = "Gerenciar Campanha", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class AcaoModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Atualizar Site", 
				new TViewItem(TDynaView.class, AcaoModelView.class, "Ação"),
				new TViewItem(TDynaView.class, SiteAboutModelView.class, "Conheça"),
				new TViewItem(TDynaView.class, SiteContatoModelView.class, "Contatos"),
				new TViewItem(TDynaView.class, SiteVideoModelView.class, "Videos"),
				new TViewItem(TDynaView.class, SiteDoacaoModelView.class, "Doação"),
				new TViewItem(TDynaView.class, SiteNoticiaModelView.class, "Noticias"),
				new TViewItem(TDynaView.class, SiteTermoModelView.class, "Termo")
				));
	}

}
