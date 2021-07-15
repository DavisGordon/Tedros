/**
 * 
 */
package com.solidarity.module.pessoa.model;

import java.util.List;

import com.solidarity.helper.TermoAdesaoHelper;
import com.solidarity.model.Pessoa;
import com.solidarity.model.TermoAdesao;
import com.solidarity.module.pessoa.process.TermoAdesaoProcess;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;

import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class PessoaTermoAdesaoNewAction extends TPresenterAction<TDynaPresenter<PessoaTermoAdesaoModelView>> {

	@Override
	public boolean runBefore(TDynaPresenter<PessoaTermoAdesaoModelView> presenter) {
		
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void runAfter(TDynaPresenter<PessoaTermoAdesaoModelView> presenter) {
		
		PessoaTermoAdesaoModelView mv = presenter.getBehavior().getModelView();
		
		TermoAdesaoProcess process = new TermoAdesaoProcess();
		process.stateProperty().addListener((a, b, c)->{
			if(c.equals(State.SUCCEEDED)) {
				List<TResult<TermoAdesao>> lst = process.getValue();
				TResult<TermoAdesao> res = lst.get(0);
				if(res.getResult().equals(EnumResult.SUCESS)) {
					TDynaPresenter<PessoaModelView> p = ((TDynaViewCrudBaseBehavior)presenter.getBehavior()).getModulePresenter();
					Pessoa pess = (Pessoa) p.getBehavior().getModelView().getModel();
					TermoAdesao e = res.getValue();
					String termo = e.getConteudo();
					termo = TermoAdesaoHelper.replaceTokensTermo(pess, null, null, termo);
					mv.getConteudo().setValue(termo);
				}
			}
		});
		
		TermoAdesao ex = new TermoAdesao();
		ex.setStatus("ATIVADO");
		process.find(ex);
		process.start();
		
		
	}

}
