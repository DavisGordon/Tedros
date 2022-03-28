package com.tedros.template.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.template.module.produto.ProdutoModule;
import com.tedros.template.module.report.ReportModule;
import com.tedros.template.module.report.icon.RelatoriosIconImageView;
import com.tedros.template.module.report.icon.RelatoriosMenuIconImageView;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{myapp.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			@TModule(type=ReportModule.class, name="#{label.reports}", menu="#{module.adm}", 
					icon=RelatoriosIconImageView.class, menuIcon=RelatoriosMenuIconImageView.class,
					description="#{module.rep.desc}"),
			@TModule(type=ProdutoModule.class, name="#{label.edit.prod}", menu="#{module.adm}", 
					/*icon=ProdutoIconImageView.class, menuIcon=ProdutoMenuIconImageView.class,*/
					description="#{module.prod.desc}")

})
@TResourceBundle(resourceName={"AppLabels"})
@TSecurity(id="APP_SOMOS", appName = "#{myapp.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
