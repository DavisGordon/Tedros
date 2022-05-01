/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 13/01/2014
 */
package org.somos.module.pessoa;

import org.somos.module.pessoa.model.AssociadoModelView;
import org.somos.module.pessoa.model.PessoaModelView;
import org.somos.module.pessoa.model.TermoAdesaoModelView;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;


/**
 * The person crud module
 *
 * @author Davis Gordon
 *
 */
@TSecurity(	id="SOMOS_CADPESS_MODULE", appName = "#{somos.name}", moduleName = "Administrativo", 
			allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CadastroDePessoaModule extends TModule{

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Gerenciar Pessoas Voluntárias", 
				new TViewItem(TDynaGroupView.class, PessoaModelView.class, "Pessoas"),
				new TViewItem(TDynaGroupView.class, TermoAdesaoModelView.class, "Termos de Adesão Modelo"),
				new TViewItem(TDynaGroupView.class, AssociadoModelView.class, "Associados em campanha")
				));
	}
}
