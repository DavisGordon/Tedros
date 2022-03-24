/**
 * 
 */
package com.tedros.fxapi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.util.TFileUtil;
import com.tedros.util.TPdfUtil;
import com.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public final class HtmlPDFExportHelper {

	/**
	 * 
	 */
	private HtmlPDFExportHelper() {
	}
	
	/**
	 * @param presenter
	 * @param output
	 * @param html
	 */
	@SuppressWarnings("rawtypes")
	public static void generate(TDynaPresenter presenter, String output, String html) {
		if(StringUtils.isNotBlank(html)) {
			try {
				TPdfUtil.convert(html, output);
				TedrosContext.openDocument(output);
				String msg = TLanguage.getInstance(null)
						.getFormatedString("#{tedros.fxapi.message.export}", output);
				presenter.getDecorator().getView().tShowModal(new TMessageBox(msg), true);
			} catch (Exception e) {
				e.printStackTrace();
				String msg = TLanguage.getInstance(null)
						.getFormatedString("#{tedros.fxapi.message.error}");
				presenter.getDecorator().getView().tShowModal(new TMessageBox(msg), true);
			}
		}
	}
	
	public static void generate(TDynaPresenter presenter, String output, Document doc) {
		if(doc != null) {
			try {
				TPdfUtil.generate(doc, output);
				TedrosContext.openDocument(output);
				String msg = TLanguage.getInstance(null)
						.getFormatedString("#{tedros.fxapi.message.export}", output);
				presenter.getDecorator().getView().tShowModal(new TMessageBox(msg), true);
			} catch (Exception e) {
				e.printStackTrace();
				String msg = TLanguage.getInstance(null)
						.getFormatedString("#{tedros.fxapi.message.error}");
				presenter.getDecorator().getView().tShowModal(new TMessageBox(msg), true);
			}
		}
	}
	
	public static String getDestFile(String nome, Date data){
		if(nome==null)
			nome = "Empty title";
		String pattern = "dd-MM-yyyy HH-mm";
		DateFormat df = new SimpleDateFormat(pattern);
		String k = data!=null ? " "+df.format(data) : "";
		String folderPath = TFileUtil.getTedrosFolderPath()+TedrosFolder.EXPORT_FOLDER.getFolder();
		return folderPath + nome + k +".pdf" ;
	}


}
