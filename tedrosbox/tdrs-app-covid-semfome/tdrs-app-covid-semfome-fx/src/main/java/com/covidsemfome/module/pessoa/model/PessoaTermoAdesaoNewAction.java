/**
 * 
 */
package com.covidsemfome.module.pessoa.model;

import java.util.List;

import com.covidsemfome.helper.TermoAdesaoHelper;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TermoAdesao;
import com.covidsemfome.module.pessoa.process.TermoAdesaoProcess;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;

import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class PessoaTermoAdesaoNewAction extends TPresenterAction {

	public PessoaTermoAdesaoNewAction() {
		super(TActionType.NEW);
	}

	@Override
	public boolean runBefore() {
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void runAfter() {
		TDynaPresenter<PessoaTermoAdesaoModelView> presenter = super.getPresenter();
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
					if(e!=null) {
						String termo = e.getConteudo();
						termo = TermoAdesaoHelper.replaceTokensTermo(pess, null, null, termo);
						mv.getConteudo().setValue(termo);
					}
				}
			}
		});
		
		TermoAdesao ex = new TermoAdesao();
		ex.setStatus("ATIVADO");
		process.find(ex);
		process.start();
		
		
	}

}
