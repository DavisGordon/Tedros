/**
 * 
 */
package org.somos.module.mailing;

import org.somos.module.mailing.model.CampanhaMailConfigModelView;
import org.somos.module.mailing.model.MailingModelView;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOMOS_MAIL_MODULE", appName = "#{somos.name}", moduleName = "Gerenciar Campanha", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class MailingModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Mailing", 
				new TViewItem(TDynaGroupView.class, MailingModelView.class, "Ação"),
				new TViewItem(TDynaGroupView.class, CampanhaMailConfigModelView.class, "Configurar Mailing de campanha")));
				
	
	}

}
