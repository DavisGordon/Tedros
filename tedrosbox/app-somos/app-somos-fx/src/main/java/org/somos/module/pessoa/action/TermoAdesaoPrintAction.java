/**
 * 
 */
package org.somos.module.pessoa.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.somos.model.TermoAdesao;
import org.somos.module.pessoa.model.TermoAdesaoModelView;

import com.tedros.fxapi.control.THTMLEditor;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.util.HtmlPDFExportHelper;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class TermoAdesaoPrintAction extends TPresenterAction{

	/**
	 * @param tActionType
	 */
	public TermoAdesaoPrintAction() {
		super(TActionType.PRINT);
	}


	@Override
	public boolean runBefore() {
		TDynaPresenter<TermoAdesaoModelView> presenter = super.getPresenter();
		TermoAdesao termo = (TermoAdesao) presenter.getBehavior().getModelView().getModel();
		THTMLEditor editor = (THTMLEditor) presenter.getBehavior().getForm().gettFieldBox("conteudo").gettControl();
		Date data = termo.getLastUpdate()!=null ? termo.getLastUpdate() : termo.getInsertDate();
		String output = this.getDestFile(termo.getTitulo(), data);
		String html = editor.gettHTMLEditor().getHtmlText();
		HtmlPDFExportHelper.generate(presenter, output, html);
		return false;
	}

	
	@Override
	public void runAfter() {
		
	}
	
	protected String getDestFile(String nome, Date data){
		if(nome==null)
			nome = "Empty title";
		String pattern = "dd-MM-yyyy HH-mm";
		DateFormat df = new SimpleDateFormat(pattern);
		String k = data!=null ? " "+df.format(data) : "";
		String folderPath = TFileUtil.getTedrosFolderPath()+TedrosFolder.EXPORT_FOLDER.getFolder();
		return folderPath + nome + k +".pdf" ;
	}

}
