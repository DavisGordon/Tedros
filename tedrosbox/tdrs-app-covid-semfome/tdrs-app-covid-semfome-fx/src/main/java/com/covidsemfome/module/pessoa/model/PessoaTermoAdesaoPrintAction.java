/**
 * 
 */
package com.covidsemfome.module.pessoa.model;

import com.tedros.fxapi.control.THTMLEditor;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;

import javafx.print.PrinterJob;
import javafx.print.PrinterJob.JobStatus;
import javafx.scene.control.Label;

/**
 * @author Davis Gordon
 *
 */
public class PessoaTermoAdesaoPrintAction extends TPresenterAction<TDynaPresenter<PessoaTermoAdesaoModelView>> {

	@Override
	public boolean runBefore(TDynaPresenter<PessoaTermoAdesaoModelView> presenter) {
		THTMLEditor editor = (THTMLEditor) presenter.getBehavior().getForm().gettFieldBox("conteudo").gettControl();
		PrinterJob job = PrinterJob.createPrinterJob();
		if(job!=null) {
			job.jobStatusProperty().addListener((a, b, c)->{
				if(c.equals(JobStatus.DONE)) {
					job.endJob();
				}
			});
			editor.gettHTMLEditor().print(job);
		}else {
			Label l = new Label("Não existe impressoras configuradas!");
			presenter.getDecorator().getView().tShowModal(l, true);
		}
		return false;
	}

	@Override
	public void runAfter(TDynaPresenter<PessoaTermoAdesaoModelView> presenter) {
		
	}

}
