/**
 * 
 */
package com.editorweb.module.site;

import com.editorweb.module.site.model.DomainMV;
import com.editorweb.module.site.model.PageMV;
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
@TSecurity(	id="TEW_SITE_MODULE", appName = "#{app.tew.name}", moduleName = "#{module.site}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class SiteModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.site}", 
				new TViewItem(TDynaGroupView.class, DomainMV.class, "#{view.domain}"),
				new TViewItem(TDynaGroupView.class, PageMV.class, "#{view.pages}")
				));
	}

}
