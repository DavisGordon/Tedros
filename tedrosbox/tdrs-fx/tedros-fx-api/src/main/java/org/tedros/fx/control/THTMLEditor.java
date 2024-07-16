/**
 * 
 */
package org.tedros.fx.control;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.util.HtmlPDFExportHelper;
import org.tedros.fx.util.TPrintUtil;
import org.tedros.util.TLoggerUtil;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
public class THTMLEditor extends TRequiredHTMLEditor {

	private ToolBar toolBar;
	private TButton tPrintButton;
	private TButton tExportPdfButton;
	private HTMLEditor editor;
	private WebPage webPage;
	private SimpleStringProperty tHtmlProperty;
	private SimpleBooleanProperty tShowActionsToolBarProperty;
	private ChangeListener<String> listener;
	
	/**
	 * 
	 */
	public THTMLEditor() {
		
		tHtmlProperty = new SimpleStringProperty();
		tShowActionsToolBarProperty = new SimpleBooleanProperty(false);
		editor = new HTMLEditor();
		
		/*if(tHtmlProperty.getValue()!=null)
			editor.setHtmlText(tHtmlProperty.getValue());*/
		tShowActionsToolBarProperty.addListener((a,b,n)->{
			this.tShowActionsToolBar(n);
		});
		listener = (a, b, c)->{
			editor.setHtmlText(c);
		};
		tHtmlProperty.addListener(listener);
		this.getChildren().addAll(editor);
		
		this.sceneProperty().addListener((ob, o, n)->{
			if(n!=null) {
				WebView webView = (WebView) editor.lookup("WebView");
				webPage = Accessor.getPageFor(webView.getEngine());
		        webView.focusedProperty().addListener((a, b, c) -> { 
		        	tHtmlProperty.removeListener(listener);
		        	String text = webPage.getInnerText(webPage.getMainFrame());
		        	if(StringUtils.isNotBlank(text))
		        		tHtmlProperty.setValue(editor.getHtmlText());
		        	else
		        		tHtmlProperty.setValue(null);
		        	tHtmlProperty.addListener(listener);
		        });
		        
		        webView.addEventFilter(KeyEvent.KEY_TYPED, e -> { 
		        	if(StringUtils.isBlank(tHtmlProperty.getValue())) {
		        		tHtmlProperty.removeListener(listener);
		        		tHtmlProperty.setValue(editor.getHtmlText());
		        		tHtmlProperty.addListener(listener);
		        	}
		        });
			}
		});
		
		
        super.tRequiredNodeProperty().setValue(gettHTMLEditor());
	}

	private void tShowActionsToolBar(boolean show) {
		if(show) {
			TLanguage ie = TLanguage.getInstance();
			if(toolBar==null)
				toolBar = new ToolBar();
			if(this.tPrintButton==null)
				this.tPrintButton = new TButton(ie.getString(TFxKey.BUTTON_PRINT));
			if(this.tExportPdfButton==null)
				this.tExportPdfButton = new TButton("PDF");

			toolBar.setId("t-view-toolbar");
			toolBar.getItems().addAll(tPrintButton, tExportPdfButton);
			super.getChildren().add(toolBar);
			
			this.tPrintButton.setOnAction(e->{
				WebView webView = (WebView) editor.lookup("WebView");
				TPrintUtil.print(webView);
			});
			
			this.tExportPdfButton.setOnAction(e->{
				WebView wv = (WebView) editor.lookup("WebView");
				if(wv!=null) {
					String output = HtmlPDFExportHelper.getDestFile("content", new Date());
					String html = (String) wv.getEngine().executeScript("document.documentElement.outerHTML");
					try {
						HtmlPDFExportHelper.generate(output, html);
					} catch (Exception e1) {
						TLoggerUtil.error(getClass(), e1.getMessage(), e1);
					}
				}
			});
		}else {
			if(toolBar!=null && super.getChildren().contains(toolBar)) {
				super.getChildren().remove(toolBar);
				toolBar = null;
				this.tPrintButton = null;
				this.tExportPdfButton = null;
			}
		}
	}
	
	
	@Override
	public HTMLEditor gettHTMLEditor() {
		return editor;
	}

	@Override
	public SimpleStringProperty tHTMLProperty() {
		return tHtmlProperty;
	}
	
	public void settHtml(String html) {
		this.tHtmlProperty.setValue(html);
	}

	/**
	 * @return the tShowActionsToolBarProperty
	 */
	public ReadOnlyBooleanProperty tShowActionsToolBarProperty() {
		return tShowActionsToolBarProperty;
	}

	/**
	 * @param tShowActionsToolBarProperty the tShowActionsToolBarProperty to set
	 */
	public void settShowActionsToolBar(boolean show) {
		this.tShowActionsToolBarProperty.setValue(show);
	}

	/**
	 * @return the tShowActionsToolBarProperty
	 */
	public Boolean gettShowActionsToolBar() {
		return tShowActionsToolBarProperty.getValue();
	}
	
}
