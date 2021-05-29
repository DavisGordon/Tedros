/**
 * 
 */
package com.covidsemfome.module.pessoa.model;

import com.tedros.fxapi.control.THTMLEditor;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;

import javafx.print.PrinterJob;

/**
 * @author Davis Gordon
 *
 */
public class PessoaTermoAdesaoPrintAction extends TPresenterAction<TDynaPresenter<PessoaTermoAdesaoModelView>> {

	@Override
	public boolean runBefore(TDynaPresenter<PessoaTermoAdesaoModelView> presenter) {
		THTMLEditor editor = (THTMLEditor) presenter.getBehavior().getForm().gettFieldBox("conteudo").gettControl();
		editor.gettHTMLEditor().print(PrinterJob.createPrinterJob());
		
		return false;
	}

	@Override
	public void runAfter(TDynaPresenter<PessoaTermoAdesaoModelView> presenter) {
		
	}

}
