/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 06/04/2014
 */
package com.tedros.fxapi.reader;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.domain.THtmlConstant;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Text;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class THtmlReader extends Text {

	private String tHtmlTemplate;
	
	public THtmlReader(String html) {
		
		if(html==null)
			throw new IllegalArgumentException("The html argument canot be null!");
		
		buildListeners();
		setText(html);
	}
	
	public THtmlReader(String htmlTemplate, String text) {
		
		if(htmlTemplate==null)
			throw new IllegalArgumentException("The htmlTemplate argument canot be null!");
		
		buildListeners();
		this.tHtmlTemplate = htmlTemplate;
		
		setText(text);
	}
	
	public String gettHtmlTemplate() {
		return tHtmlTemplate;
	}
	
	public void settHtmlTemplate(String tHtmlTemplate) {
		this.tHtmlTemplate = tHtmlTemplate;
		String text = getText();
		setText(null);
		setText(text);
	}

	private void setFormatedText(String text) {
		setText(tHtmlTemplate.replaceAll(THtmlConstant.CONTENT, text));
	}
	
	private void buildListeners() {
		final THtmlReader _this = this;
		textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newVal) {
				if(StringUtils.isNotBlank(newVal) && StringUtils.isNotBlank(tHtmlTemplate)){
					_this.textProperty().removeListener(this);
					setFormatedText(newVal);
					_this.textProperty().addListener(this);
				}
			}
		});
	}
}