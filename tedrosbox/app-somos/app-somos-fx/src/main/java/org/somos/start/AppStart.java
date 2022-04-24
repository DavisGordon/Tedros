package org.somos.start;

import org.somos.module.acao.AcaoModule;
import org.somos.module.acao.MailingModule;
import org.somos.module.acao.icon.AcaoIconImageView;
import org.somos.module.acao.icon.AcaoMenuIconImageView;
import org.somos.module.acao.icon.MailingIconImageView;
import org.somos.module.acao.icon.MailingMenuIconImageView;
import org.somos.module.cozinha.CozinhaModule;
import org.somos.module.cozinha.icon.CozinhaIconImageView;
import org.somos.module.cozinha.icon.CozinhaMenuIconImageView;
import org.somos.module.estoque.EstoqueModule;
import org.somos.module.estoque.icon.EstoqueIconImageView;
import org.somos.module.estoque.icon.EstoqueMenuIconImageView;
import org.somos.module.pessoa.CadastroDePessoaModule;
import org.somos.module.pessoa.icon.PessoaIconImageView;
import org.somos.module.pessoa.icon.PessoaMenuIconImageView;
import org.somos.module.report.ReportModule;
import org.somos.module.report.icon.RelatoriosIconImageView;
import org.somos.module.report.icon.RelatoriosMenuIconImageView;
import org.somos.module.tipoAjuda.TipoAjudaModule;
import org.somos.module.tipoAjuda.icon.TipoAjudaIconImageView;
import org.somos.module.tipoAjuda.icon.TipoAjudaMenuIconImageView;

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
@TApplication(name="#{somos.name}", universalUniqueIdentifier=TConstant.UUI,
module = {	
			@TModule(type=TipoAjudaModule.class, name="Tipos de Ajuda", menu="Gerenciar Campanha", 
					icon=TipoAjudaIconImageView.class, menuIcon=TipoAjudaMenuIconImageView.class,
					description="Edite aqui os Tipos de Ajuda que os voluntarios podem executar."),
			@TModule(type=AcaoModule.class, name="Ação/Site", menu="Gerenciar Campanha", 
					icon=AcaoIconImageView.class, menuIcon=AcaoMenuIconImageView.class,
					description="Administre aqui as ações/campanhas e algumas informações do site"),
			@TModule(type=MailingModule.class, name="Mailing", menu="Gerenciar Campanha", 
					icon=MailingIconImageView.class, menuIcon=MailingMenuIconImageView.class,
					description="Crie e envie e-mails a um grupo de pessoas sobre uma determinada ação."),
			@TModule(type=ReportModule.class, name="Relatórios", menu="Administrativo", 
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
			@TModule(type=EstoqueModule.class, name="Estoque", menu="Administrativo", 
					icon=EstoqueIconImageView.class, menuIcon=EstoqueMenuIconImageView.class,
					description="Gerencie aqui os produtos as entradas e saídas do estoque, edite o "
							+ "estoque inicial e visualize os estoques gerados. "
							+ "Necessario inserir os locais de produção.")

})
@TResourceBundle(resourceName={"SomosLabels"})
@TSecurity(id="APP_SOMOS", appName = "#{somos.name}", allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}
