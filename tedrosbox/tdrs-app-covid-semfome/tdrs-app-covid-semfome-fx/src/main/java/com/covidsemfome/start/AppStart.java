package com.covidsemfome.start;

import com.covidsemfome.module.acao.AcaoModule;
import com.covidsemfome.module.acao.MailingModule;
import com.covidsemfome.module.acao.icon.AcaoIconImageView;
import com.covidsemfome.module.acao.icon.AcaoMenuIconImageView;
import com.covidsemfome.module.acao.icon.MailingIconImageView;
import com.covidsemfome.module.acao.icon.MailingMenuIconImageView;
import com.covidsemfome.module.doacao.DoacaoModule;
import com.covidsemfome.module.doacao.icon.DoacaoIconImageView;
import com.covidsemfome.module.doacao.icon.DoacaoMenuIconImageView;
import com.covidsemfome.module.pessoa.CadastroDePessoaModule;
import com.covidsemfome.module.pessoa.icon.PessoaIconImageView;
import com.covidsemfome.module.pessoa.icon.PessoaMenuIconImageView;
import com.covidsemfome.module.report.DoacaoReportModule;
import com.covidsemfome.module.voluntario.CadastroTipoAjudaModule;
import com.covidsemfome.module.voluntario.CadastroVoluntarioModule;
import com.covidsemfome.module.voluntario.icon.TipoAjudaIconImageView;
import com.covidsemfome.module.voluntario.icon.TipoAjudaMenuIconImageView;
import com.covidsemfome.module.voluntario.icon.VoluntarioIconImageView;
import com.covidsemfome.module.voluntario.icon.VoluntarioMenuIconImageView;
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
module = {	
			@TModule(type=CadastroTipoAjudaModule.class, name="Cadastrar Tipo Ajuda", menu="Gerenciar Campanha", 
					icon=TipoAjudaIconImageView.class, menuIcon=TipoAjudaMenuIconImageView.class),
			@TModule(type=CadastroDePessoaModule.class, name="#{label.person}", menu="Gerenciar Campanha", 
					icon=PessoaIconImageView.class, menuIcon=PessoaMenuIconImageView.class),
			@TModule(type=AcaoModule.class, name="Ação", menu="Gerenciar Campanha", 
					icon=AcaoIconImageView.class, menuIcon=AcaoMenuIconImageView.class),
			@TModule(type=CadastroVoluntarioModule.class, name="Voluntários inscritos", menu="Gerenciar Campanha", 
					icon=VoluntarioIconImageView.class, menuIcon=VoluntarioMenuIconImageView.class),
			@TModule(type=DoacaoModule.class, name="Doação", menu="Gerenciar Campanha", 
					icon=DoacaoIconImageView.class, menuIcon=DoacaoMenuIconImageView.class),
			@TModule(type=MailingModule.class, name="Mailing", menu="Gerenciar Campanha", 
					icon=MailingIconImageView.class, menuIcon=MailingMenuIconImageView.class),
			@TModule(type=DoacaoReportModule.class, name="Doações", menu="Relatorios"/*, 
			icon=MailingIconImageView.class, menuIcon=MailingMenuIconImageView.class*/)

})
@TResourceBundle(resourceName={"CovidLabels"})
@TSecurity(id="APP_COVIDSEMFOME", appName = "#{app.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
