/**
 * 
 */
package org.somos.module.acao;

import org.somos.module.acao.campanha.model.CampanhaModelView;
import org.somos.module.acao.campanha.model.FormaAjudaModelView;
import org.somos.module.acao.campanha.model.ValorAjudaModelView;
import org.somos.module.acao.model.AcaoModelView;
import org.somos.module.acao.model.SiteAboutModelView;
import org.somos.module.acao.model.SiteContatoModelView;
import org.somos.module.acao.model.SiteEquipeModelView;
import org.somos.module.acao.model.SiteGaleriaModelView;
import org.somos.module.acao.model.SiteNoticiaModelView;
import org.somos.module.acao.model.SiteParceriaModelView;
import org.somos.module.acao.model.SiteSMDoacaoModelView;
import org.somos.module.acao.model.SiteSocialMidiaModelView;
import org.somos.module.acao.model.SiteTermoModelView;
import org.somos.module.acao.model.SiteVideoModelView;
import org.somos.module.acao.model.SiteVoluntarioModelView;

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
				new TViewItem(TDynaGroupView.class, CampanhaModelView.class, "Campanha de ajuda"),
				new TViewItem(TDynaGroupView.class, FormaAjudaModelView.class, "Forma de ajuda em campanha"),
				new TViewItem(TDynaGroupView.class, ValorAjudaModelView.class, "Valor de ajuda em campanha"),
				new TViewItem(TDynaGroupView.class, SiteAboutModelView.class, "Site/Home/Introdução"),
				new TViewItem(TDynaGroupView.class, SiteContatoModelView.class, "Site/Home/Contatos"),
				new TViewItem(TDynaGroupView.class, SiteVideoModelView.class, "Site/Home/Videos"),
				new TViewItem(TDynaGroupView.class, SiteNoticiaModelView.class, "Site/Home/Noticias"),
				new TViewItem(TDynaGroupView.class, SiteEquipeModelView.class, "Site/Home/Equipe"),
				new TViewItem(TDynaGroupView.class, SiteTermoModelView.class, "Site/Meu Painel/Termo"),
				new TViewItem(TDynaGroupView.class, SiteGaleriaModelView.class, "Site/Galeria"),
				new TViewItem(TDynaGroupView.class, SiteSMDoacaoModelView.class, "Site/Doações"),
				new TViewItem(TDynaGroupView.class, SiteParceriaModelView.class, "Site/Parceria"),
				new TViewItem(TDynaGroupView.class, SiteVoluntarioModelView.class, "Site/Voluntario"),
				new TViewItem(TDynaGroupView.class, SiteSocialMidiaModelView.class, "Site/Rodapé/Siga nos")
				));
	}

}
