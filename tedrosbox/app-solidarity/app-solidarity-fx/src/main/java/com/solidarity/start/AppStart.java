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
			@TModule(type=CadastroTipoAjudaModule.class, name="Tipos de Ajuda", menu="Gerenciar Campanha", 
					icon=TipoAjudaIconImageView.class, menuIcon=TipoAjudaMenuIconImageView.class,
					description="Edite aqui os Tipos de Ajuda que os voluntarios podem executar."),
			@TModule(type=AcaoModule.class, name="Ação/Site", menu="Gerenciar Campanha", 
					icon=AcaoIconImageView.class, menuIcon=AcaoMenuIconImageView.class,
					description="Administre aqui as ações/campanhas e algumas informações do site"),
			@TModule(type=CadastroVoluntarioModule.class, name="Voluntários inscritos", menu="Gerenciar Campanha", 
					icon=VoluntarioIconImageView.class, menuIcon=VoluntarioMenuIconImageView.class,
					description="Edite/visualize aqui os voluntarios e o tipo de ajuda nas ações."),
			@TModule(type=MailingModule.class, name="Mailing", menu="Gerenciar Campanha", 
					icon=MailingIconImageView.class, menuIcon=MailingMenuIconImageView.class,
					description="Crie e envie e-mails a um grupo de pessoas sobre uma determinada ação."),
			@TModule(type=DoacaoReportModule.class, name="Relatórios", menu="Administrativo", 
					icon=RelatoriosIconImageView.class, menuIcon=RelatoriosMenuIconImageView.class,
					description="Gere relatórios que ajudem nos processos administrativos."),
			@TModule(type=CadastroDePessoaModule.class, name="#{label.person}", menu="Administrativo", 
					icon=PessoaIconImageView.class, menuIcon=PessoaMenuIconImageView.class,
					description="Gerencie aqui todas as pessoas envolvidas no projeto, voluntarios e "
							+ "filantropicos cadastrados pelo site e também os termos de adesão."),
			@TModule(type=CozinhaModule.class, name="Local de Produção", menu="Administrativo", 
					icon=CozinhaIconImageView.class, menuIcon=CozinhaMenuIconImageView.class,
					description="Edite aqui os locais (cozinhas) de produção, necessario "
							+ "para editar estoque e inserir entradas e saidas de produtos."),
			@TModule(type=ProdutoModule.class, name="Produto", menu="Administrativo", 
					icon=ProdutoIconImageView.class, menuIcon=ProdutoMenuIconImageView.class,
					description="Insira/Importe aqui os produtos utilizados na produção e informe "
							+ "as entradas (Compras) e saídas (Produção). "
							+ "Necessario editar o estoque inicial antes de lançar as entradas e saídas."),
			@TModule(type=EstoqueModule.class, name="Estoque", menu="Administrativo", 
					icon=EstoqueIconImageView.class, menuIcon=EstoqueMenuIconImageView.class,
					description="Edite o estoque inicial e visualize os estoques gerados apos o lançamento "
							+ "das entradas e saidas de produtos. Necessario inserir os locais de produção "
							+ "e os produtos primeiro.")

})
@TResourceBundle(resourceName={"CovidLabels"})
@TSecurity(id="APP_COVIDSEMFOME", appName = "#{app.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}