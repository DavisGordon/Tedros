/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package org.somos.module;

import org.somos.module.web.model.ComponentTemplateMV;
import org.somos.module.web.model.CssClassMV;

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
@TSecurity(	id="SOMOS_WEB_MODULE", appName = "#{somos.name}", moduleName = "Administrativo", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class WebModule extends TModule{

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Website template", 
				new TViewItem(TDynaGroupView.class, CssClassMV.class, "Css"),
				new TViewItem(TDynaGroupView.class, ComponentTemplateMV.class, "#{label.component}")
				));
	}
}
