package com.covidsemfome.start;

import com.covidsemfome.module.cozinha.CozinhaModule;
import com.covidsemfome.module.cozinha.icon.CozinhaIconImageView;
import com.covidsemfome.module.cozinha.icon.CozinhaMenuIconImageView;
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
@TApplication(name="#{app.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			
			@TModule(type=CozinhaModule.class, name="Local de Produção", menu="Administrativo", 
					icon=CozinhaIconImageView.class, menuIcon=CozinhaMenuIconImageView.class,
					description="Edite aqui os locais (cozinhas) de produção, necessario "
							+ "para editar estoque e inserir entradas e saidas de produtos.")

})
@TResourceBundle(resourceName={"CovidLabels"})
@TSecurity(id="APP_COVIDSEMFOME", appName = "#{app.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		
	}
	
	
}
