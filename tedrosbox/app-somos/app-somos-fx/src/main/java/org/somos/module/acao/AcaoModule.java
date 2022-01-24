/**
 * 
 */
package org.somos.module.acao;

import org.somos.module.acao.model.AcaoModelView;
import org.somos.module.acao.model.SiteAboutModelView;
import org.somos.module.acao.model.SiteContatoModelView;
import org.somos.module.acao.model.SiteDoacaoModelView;
import org.somos.module.acao.model.SiteNoticiaModelView;
import org.somos.module.acao.model.SiteTermoModelView;
import org.somos.module.acao.model.SiteVideoModelView;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOMOS_ACAOSITE_MODULE", appName = "#{somos.name}", moduleName = "Gerenciar Campanha", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class AcaoModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Atualizar Site", 
				new TViewItem(TDynaGroupView.class, AcaoModelView.class, "Ação"),
				new TViewItem(TDynaGroupView.class, SiteAboutModelView.class, "Conheça"),
				new TViewItem(TDynaGroupView.class, SiteContatoModelView.class, "Contatos"),
				new TViewItem(TDynaGroupView.class, SiteVideoModelView.class, "Videos"),
				new TViewItem(TDynaGroupView.class, SiteDoacaoModelView.class, "Doação"),
				new TViewItem(TDynaGroupView.class, SiteNoticiaModelView.class, "Noticias"),
				new TViewItem(TDynaGroupView.class, SiteTermoModelView.class, "Termo")
				));
	}

}
