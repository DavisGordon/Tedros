/**
 * 
 */
package com.tedros.fxapi.control;

import org.apache.commons.lang3.StringUtils;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
public class THTMLEditor extends TRequiredHTMLEditor {

	private HTMLEditor editor;
	private WebPage webPage;
	private SimpleStringProperty htmlProperty;
	private ChangeListener<String> listener;
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	public THTMLEditor(SimpleStringProperty property) {
		
		editor = new HTMLEditor();
		if(property.getValue()!=null)
			editor.setHtmlText(property.getValue());
		htmlProperty = property;
		
		listener = (a, b, c)->{
			editor.setHtmlText(c);
		};
		htmlProperty.addListener(listener);
		this.getChildren().addAll(editor);
		
		WebView webView = (WebView) editor.lookup("WebView");
		webPage = Accessor.getPageFor(webView.getEngine());
        webView.focusedProperty().addListener((a, b, c) -> { 
        	htmlProperty.removeListener(listener);
        	String text = webPage.getInnerText(webPage.getMainFrame());
        	if(StringUtils.isNotBlank(text))
        		htmlProperty.setValue(editor.getHtmlText());
        	else
        		htmlProperty.setValue(null);
        	htmlProperty.addListener(listener);
        });
        
        webView.addEventFilter(KeyEvent.KEY_TYPED, e -> { 
        	if(StringUtils.isBlank(htmlProperty.getValue())) {
        		htmlProperty.removeListener(listener);
        		htmlProperty.setValue(editor.getHtmlText());
        		htmlProperty.addListener(listener);
        	}
        });
        super.tRequiredNodeProperty().setValue(gettHTMLEditor());
	}

	@Override
	public HTMLEditor gettHTMLEditor() {
		return editor;
	}

	@Override
	public SimpleStringProperty gettHTMLProperty() {
		return htmlProperty;
	}
	
}
