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
@TSecurity(	id="SOLIDARITY_ACAOSITE_MODULE", appName = "#{app.name}", moduleName = "#{module.manage.campaign}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class AcaoModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.web}", 
				new TViewItem(TDynaView.class, AcaoModelView.class, "#{view.web.campaign}"),
				new TViewItem(TDynaView.class, SiteAboutModelView.class, "#{view.web.about}"),
				new TViewItem(TDynaView.class, SiteContatoModelView.class, "#{view.web.contato}"),
				new TViewItem(TDynaView.class, SiteVideoModelView.class, "#{view.web.videos}"),
				new TViewItem(TDynaView.class, SiteDoacaoModelView.class, "#{view.web.doacao}"),
				new TViewItem(TDynaView.class, SiteNoticiaModelView.class, "#{view.web.noticias}"),
				new TViewItem(TDynaView.class, SiteTermoModelView.class, "#{view.web.termo}")
				));
	}

}
