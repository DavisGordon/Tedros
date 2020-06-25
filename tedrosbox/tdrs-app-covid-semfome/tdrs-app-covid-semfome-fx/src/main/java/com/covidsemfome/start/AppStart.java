package com.covidsemfome.start;

import com.covidsemfome.module.acao.AcaoModule;
import com.covidsemfome.module.acao.icon.AcaoIconImageView;
import com.covidsemfome.module.acao.icon.AcaoMenuIconImageView;
import com.covidsemfome.module.doador.DoadorModule;
import com.covidsemfome.module.doador.icon.DoadorIconImageView;
import com.covidsemfome.module.doador.icon.DoadorMenuIconImageView;
import com.covidsemfome.module.pessoa.CadastroDePessoaModule;
import com.covidsemfome.module.pessoa.icon.PessoaIconImageView;
import com.covidsemfome.module.pessoa.icon.PessoaMenuIconImageView;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.context.ITApplication;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name="#{app.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	@TModule(type=DoadorModule.class, name="#{label.donor}", menu="#{app.menu.donor}", 
					icon=DoadorIconImageView.class, menuIcon=DoadorMenuIconImageView.class),
			@TModule(type=CadastroDePessoaModule.class, name="#{label.person}", menu="#{menu.pessoa}", 
					icon=PessoaIconImageView.class, menuIcon=PessoaMenuIconImageView.class),
			@TModule(type=AcaoModule.class, name="Painel do voluntário", menu="Ação", 
			icon=AcaoIconImageView.class, menuIcon=AcaoMenuIconImageView.class)})
@TResourceBundle(resourceName={"CovidLabels"})
@TSecurity(id="APP_COVIDSEMFOME", appName = "#{app.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	
}
