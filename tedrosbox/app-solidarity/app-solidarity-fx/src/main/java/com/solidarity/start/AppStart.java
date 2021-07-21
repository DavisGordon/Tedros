package com.solidarity.start;

import com.solidarity.module.acao.AcaoModule;
import com.solidarity.module.acao.MailingModule;
import com.solidarity.module.acao.icon.AcaoIconImageView;
import com.solidarity.module.acao.icon.AcaoMenuIconImageView;
import com.solidarity.module.acao.icon.MailingIconImageView;
import com.solidarity.module.acao.icon.MailingMenuIconImageView;
import com.solidarity.module.cozinha.CozinhaModule;
import com.solidarity.module.cozinha.icon.CozinhaIconImageView;
import com.solidarity.module.cozinha.icon.CozinhaMenuIconImageView;
import com.solidarity.module.estoque.EstoqueModule;
import com.solidarity.module.estoque.icon.EstoqueIconImageView;
import com.solidarity.module.estoque.icon.EstoqueMenuIconImageView;
import com.solidarity.module.pessoa.CadastroDePessoaModule;
import com.solidarity.module.pessoa.icon.PessoaIconImageView;
import com.solidarity.module.pessoa.icon.PessoaMenuIconImageView;
import com.solidarity.module.produto.ProdutoModule;
import com.solidarity.module.produto.icon.ProdutoIconImageView;
import com.solidarity.module.produto.icon.ProdutoMenuIconImageView;
import com.solidarity.module.report.DoacaoReportModule;
import com.solidarity.module.report.icon.RelatoriosIconImageView;
import com.solidarity.module.report.icon.RelatoriosMenuIconImageView;
import com.solidarity.module.voluntario.CadastroTipoAjudaModule;
import com.solidarity.module.voluntario.CadastroVoluntarioModule;
import com.solidarity.module.voluntario.icon.TipoAjudaIconImageView;
import com.solidarity.module.voluntario.icon.TipoAjudaMenuIconImageView;
import com.solidarity.module.voluntario.icon.VoluntarioIconImageView;
import com.solidarity.module.voluntario.icon.VoluntarioMenuIconImageView;
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
			@TModule(type=CadastroTipoAjudaModule.class, name="#{view.tipoajuda}", menu="#{module.manage.campaign}", 
					icon=TipoAjudaIconImageView.class, menuIcon=TipoAjudaMenuIconImageView.class,
					description="#{view.tipoajuda.desc}"),
			@TModule(type=AcaoModule.class, name="#{view.web}", menu="#{module.manage.campaign}", 
					icon=AcaoIconImageView.class, menuIcon=AcaoMenuIconImageView.class,
					description="#{view.web.desc}"),
			@TModule(type=CadastroVoluntarioModule.class, name="#{view.vol.inscritos}", menu="#{module.manage.campaign}", 
					icon=VoluntarioIconImageView.class, menuIcon=VoluntarioMenuIconImageView.class,
					description="#{view.vol.inscritos.desc}"),
			@TModule(type=MailingModule.class, name="#{view.emailing}", menu="#{module.manage.campaign}", 
					icon=MailingIconImageView.class, menuIcon=MailingMenuIconImageView.class,
					description="#{view.emailing.desc}"),
			@TModule(type=DoacaoReportModule.class, name="#{view.relatorios}", menu="#{module.administrativo}", 
					icon=RelatoriosIconImageView.class, menuIcon=RelatoriosMenuIconImageView.class,
					description="#{view.relatorios.desc}"),
			@TModule(type=CadastroDePessoaModule.class, name="#{view.pessoa}", menu="#{module.administrativo}", 
					icon=PessoaIconImageView.class, menuIcon=PessoaMenuIconImageView.class,
					description="#{view.pessoa.desc}"),
			@TModule(type=CozinhaModule.class, name="#{view.local.prod}", menu="#{module.administrativo}", 
					icon=CozinhaIconImageView.class, menuIcon=CozinhaMenuIconImageView.class,
					description="#{view.local.prod.desc}"),
			@TModule(type=ProdutoModule.class, name="#{view.produtos}", menu="#{module.administrativo}", 
					icon=ProdutoIconImageView.class, menuIcon=ProdutoMenuIconImageView.class,
					description="#{view.produtos.desc}"),
			@TModule(type=EstoqueModule.class, name="#{view.estoque}", menu="#{module.administrativo}", 
					icon=EstoqueIconImageView.class, menuIcon=EstoqueMenuIconImageView.class,
					description="#{view.estoque.desc}")

})
@TResourceBundle(resourceName={"Solidarity"})
@TSecurity(id="APP_SOLIDARITY", appName = "#{app.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
