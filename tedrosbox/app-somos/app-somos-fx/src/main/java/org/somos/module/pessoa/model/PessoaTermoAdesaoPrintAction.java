/**
 * 
 */
package org.somos.module.pessoa.model;

import org.somos.model.Pessoa;
import org.somos.model.PessoaTermoAdesao;

import com.tedros.fxapi.control.THTMLEditor;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.util.HtmlPDFExportHelper;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class PessoaTermoAdesaoPrintAction extends TPresenterAction{

	
	/**
	 * @param tActionType
	 */
	public PessoaTermoAdesaoPrintAction() {
		super(TActionType.PRINT);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean runBefore() {
		TDynaPresenter<PessoaTermoAdesaoModelView> presenter = super.getPresenter();
		TDynaPresenter<PessoaModelView> p = ((TDynaViewCrudBaseBehavior)presenter.getBehavior()).getModulePresenter();
		Pessoa pess = (Pessoa) p.getBehavior().getModelView().getModel();
		PessoaTermoAdesao termo = (PessoaTermoAdesao) presenter.getBehavior().getModelView().getModel();
		THTMLEditor editor = (THTMLEditor) presenter.getBehavior().getForm().gettFieldBox("conteudo").gettControl();
		String output = this.getDestFile(pess.getNome(), termo.getVersionNum());
		String html = editor.gettHTMLEditor().getHtmlText();
		HtmlPDFExportHelper.generate(presenter, output, html);
		return false;
	}

	
	@Override
	public void runAfter() {
		
	}
	
	protected String getDestFile(String nome, Integer id){
		if(nome==null)
			nome = "Empty name";
		if(id==null)
			id = 0;
		String folderPath = TFileUtil.getTedrosFolderPath()+TedrosFolder.EXPORT_FOLDER.getFolder();
		return folderPath +nome+" - Termo Adesao (v."+ id.toString()+").pdf" ;
	}

}
