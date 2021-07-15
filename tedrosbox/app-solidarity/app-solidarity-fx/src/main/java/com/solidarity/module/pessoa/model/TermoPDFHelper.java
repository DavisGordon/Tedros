/**
 * 
 */
package com.solidarity.module.pessoa.model;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.modal.TMessageBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.util.TPdfUtil;

/**
 * @author Davis Gordon
 *
 */
final class TermoPDFHelper {

	/**
	 * 
	 */
	private TermoPDFHelper() {
	}
	
	/**
	 * @param presenter
	 * @param output
	 * @param html
	 */
	@SuppressWarnings("rawtypes")
	static void generate(TDynaPresenter presenter, String output, String html) {
		if(StringUtils.isNotBlank(html)) {
			try {
				TPdfUtil.convert(html, output);
				TedrosContext.openDocument(output);
				String msg = TInternationalizationEngine.getInstance(null)
						.getFormatedString("#{tedros.fxapi.message.export}", output);
				presenter.getDecorator().getView().tShowModal(new TMessageBox(msg), true);
			} catch (IOException e) {
				e.printStackTrace();
				String msg = TInternationalizationEngine.getInstance(null)
						.getFormatedString("#{tedros.fxapi.message.error}");
				presenter.getDecorator().getView().tShowModal(new TMessageBox(msg), true);
			}
		}
	}
	



}
