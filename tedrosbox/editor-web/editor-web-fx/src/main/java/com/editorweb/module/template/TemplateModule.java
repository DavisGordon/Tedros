/**
 * 
 */
package com.editorweb.module.template;

import com.editorweb.module.template.model.CssClassMV;
import com.editorweb.module.template.model.HtmlTemplateMV;
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
@TSecurity(	id="TEW_TEMPLATE_MODULE", appName = "#{app.name}", moduleName = "#{module.template}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class TemplateModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "#{view.template}", 
				new TViewItem(TDynaGroupView.class, CssClassMV.class, "#{view.cssclass}"),
				new TViewItem(TDynaGroupView.class, HtmlTemplateMV.class, "#{view.htmltemplate}")
				));
	}

}
