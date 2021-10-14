package com.editorweb.start;

import com.editorweb.module.site.SiteModule;
import com.editorweb.module.template.TemplateModule;
import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{app.tew.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			@TModule(type=TemplateModule.class, name="#{view.template}", menu="#{module.template}", /*
					icon=TipoAjudaIconImageView.class, menuIcon=TipoAjudaMenuIconImageView.class,*/
					description="#{module.template.desc}"),
			@TModule(type=SiteModule.class, name="#{view.site}", menu="#{module.site}", /*
			icon=TipoAjudaIconImageView.class, menuIcon=TipoAjudaMenuIconImageView.class,*/
			description="#{module.template.desc}")

})
@TResourceBundle(resourceName={"TEWLabels"})
@TSecurity(id="TEW_APP", appName = "#{app.tew.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		
	}
	
	
}
