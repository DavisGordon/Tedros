package com.tedros.global.brasil.start;

import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.context.ITApplication;
import com.tedros.global.brasil.module.pessoa.CadastroDePessoaModule;
import com.tedros.global.brasil.module.pessoa.icon.PessoaIconImageView;
import com.tedros.global.brasil.module.pessoa.icon.PessoaMenuIconImageView;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{app.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	@TModule(type=CadastroDePessoaModule.class, name="#{label.person}", menu="#{menu.pessoa}", 
					icon=PessoaIconImageView.class, menuIcon=PessoaMenuIconImageView.class)})
@TResourceBundle(resourceName={"Labels"})
@TSecurity(id="T_APP_GLOBAL_BRASIL", appName = "#{app.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	
}
