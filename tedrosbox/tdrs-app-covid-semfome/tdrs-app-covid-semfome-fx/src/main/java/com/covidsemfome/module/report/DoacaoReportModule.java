/**
 * 
 */
package com.covidsemfome.module.report;

import com.covidsemfome.module.report.model.AcoesReportModelView;
import com.covidsemfome.module.report.model.EntradaReportModelView;
import com.covidsemfome.module.report.model.EstoqueReportModelView;
import com.covidsemfome.module.report.model.PessoaReportModelView;
import com.covidsemfome.module.report.model.ProdutoReportModelView;
import com.covidsemfome.module.report.model.SaidaReportModelView;
import com.covidsemfome.module.report.model.VoluntariosReportModelView;
import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id="COVSEMFOME_REPORT_MODULE", appName = "#{app.name}", moduleName = "Administrativo", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DoacaoReportModule extends TModule {

	@Override
	public void tStart() {
		tShowView(new TGroupView<TGroupPresenter>(this, "Relatórios", 
				new TViewItem(TDynaGroupView.class, AcoesReportModelView.class, "Ação"),
				new TViewItem(TDynaGroupView.class, VoluntariosReportModelView.class, "Lista de voluntário inscritos"),
				new TViewItem(TDynaGroupView.class, PessoaReportModelView.class, "Pessoa"),
				new TViewItem(TDynaGroupView.class, ProdutoReportModelView.class, "Produto"),
				new TViewItem(TDynaGroupView.class, EntradaReportModelView.class, "Entrada de produtos no estoque"),
				new TViewItem(TDynaGroupView.class, SaidaReportModelView.class, "Saída de produtos do estoque"),
				new TViewItem(TDynaGroupView.class, EstoqueReportModelView.class, "Estoque")
				));
	}

}
