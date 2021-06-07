/**
 * 
 */
package com.covidsemfome.module.pessoa.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.covidsemfome.model.TermoAdesao;
import com.tedros.fxapi.control.THTMLEditor;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

/**
 * @author Davis Gordon
 *
 */
public class TermoAdesaoPrintAction extends TPresenterAction<TDynaPresenter<TermoAdesaoModelView>> {

	@Override
	public boolean runBefore(TDynaPresenter<TermoAdesaoModelView> presenter) {
		TermoAdesao termo = (TermoAdesao) presenter.getBehavior().getModelView().getModel();
		THTMLEditor editor = (THTMLEditor) presenter.getBehavior().getForm().gettFieldBox("conteudo").gettControl();
		Date data = termo.getLastUpdate()!=null ? termo.getLastUpdate() : termo.getInsertDate();
		String output = this.getDestFile(termo.getTitulo(), data);
		String html = editor.gettHTMLEditor().getHtmlText();
		TermoPDFHelper.generate(presenter, output, html);
		return false;
	}

	
	@Override
	public void runAfter(TDynaPresenter<TermoAdesaoModelView> presenter) {
		
	}
	
	protected String getDestFile(String nome, Date data){
		if(nome==null)
			nome = "Empty title";
		String pattern = "dd-MM-yyyy HH-mm";
		DateFormat df = new SimpleDateFormat(pattern);
		String k = data!=null ? " "+df.format(data) : "";
		String folderPath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.EXPORT_FOLDER.getFolder();
		return folderPath + nome + k +".pdf" ;
	}

}
