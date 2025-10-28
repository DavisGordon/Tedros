/**
 * 
 */
package org.tedros.fx.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.util.TFileUtil;
import org.tedros.util.TPdfUtil;
import org.tedros.util.TedrosFolder;
import org.w3c.dom.Document;

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
	 * @param output
	 * @param html
	 * @throws Exception 
	 */
	public static void generate(String output, String html) throws Exception {
		if(StringUtils.isNotBlank(html)) {
			TPdfUtil.convert(html, output);
			TedrosContext.openDocument(output);
		}
	}
	
	public static void generate(String output, Document doc) throws Exception {
		if(doc != null) {
			TPdfUtil.generate(doc, output);
			TedrosContext.openDocument(output);
		}
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
				generate(output, html);
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
	
	@SuppressWarnings("rawtypes")
	public static void generate(TDynaPresenter presenter, String output, Document doc) {
		if(doc != null) {
			try {
				generate(output, doc);
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
