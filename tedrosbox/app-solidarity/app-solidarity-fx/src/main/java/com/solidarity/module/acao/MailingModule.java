/**
 * 
 */
package com.solidarity.module.acao;

import com.solidarity.module.acao.model.MailingModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOLIDARITY_MAIL_MODULE", appName = "#{app.name}", moduleName = "#{module.manage.campaign}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class MailingModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		TDynaView<MailingModelView> view = new TDynaView<>(this, MailingModelView.class);
		tShowView(view);
	}

}
