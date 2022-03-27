package org.somos.start;

import org.somos.module.produto.ProdutoModule;
import org.somos.module.produto.icon.ProdutoIconImageView;
import org.somos.module.produto.icon.ProdutoMenuIconImageView;
import org.somos.module.report.ReportModule;
import org.somos.module.report.icon.RelatoriosIconImageView;
import org.somos.module.report.icon.RelatoriosMenuIconImageView;

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
@TApplication(name="#{myapp.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			@TModule(type=ReportModule.class, name="Relatórios", menu="Administrativo", 
					icon=RelatoriosIconImageView.class, menuIcon=RelatoriosMenuIconImageView.class,
					description="Gere relatórios que ajudem nos processos administrativos."),
			@TModule(type=ProdutoModule.class, name="Editar Produto", menu="Administrativo", 
					/*icon=ProdutoIconImageView.class, menuIcon=ProdutoMenuIconImageView.class,*/
					description="Gerencie aqui os produtos.")

})
@TResourceBundle(resourceName={"AppLabels"})
@TSecurity(id="APP_SOMOS", appName = "#{myapp.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
