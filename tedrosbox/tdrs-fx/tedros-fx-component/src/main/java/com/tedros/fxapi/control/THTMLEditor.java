/**
 * 
 */
package com.tedros.fxapi.control;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        this.addHyperlinkButton();
	}
	
	private void addHyperlinkButton() {
		Node node = editor.lookup(".top-toolbar");
		if (node instanceof ToolBar) {
			ToolBar bar = (ToolBar) node;
			Button btn = new Button("Hyperlink");
			ImageView iv = new javafx.scene.image.ImageView(new Image(getClass().getResourceAsStream("link.png")));
			btn.setMinSize(26.0, 22.0);
			btn.setMaxSize(26.0, 22.0);
			iv.setFitHeight(16);
			iv.setFitWidth(16);
			btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			btn.setGraphic(iv);
			btn.setTooltip(new javafx.scene.control.Tooltip("Hyperlink"));
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					WebView webView = (WebView) editor.lookup("WebView");
					String html = (String) webView.getEngine().executeScript(getSelectedHtml());
					String url = JOptionPane.showInputDialog("Enter Url", html);
					if(StringUtils.isNotBlank(url)) {
						
						String selected = (String) webView.getEngine().executeScript("window.getSelection().toString();");
						String hyperlinkHtml = "<a href=\"" + url.trim() + "\" title=\"" + selected + "\" target=\"_blank\">" + selected + "</a>";
						webView.getEngine().executeScript(getInsertHtmlAtCursorJS(hyperlinkHtml));
					}
				}
			});
			bar.getItems().add(btn);
		}
	}

	private String getSelectedHtml() {
		return "getHTMLOfSelection();\r\n"
				+ "function getHTMLOfSelection () {\r\n" + 
				"      var range;\r\n" + 
				"      if (document.selection && document.selection.createRange) {\r\n" + 
				"        range = document.selection.createRange();\r\n" + 
				"        return range.htmlText;\r\n" + 
				"      }\r\n" + 
				"      else if (window.getSelection) {\r\n" + 
				"        var selection = window.getSelection();\r\n" + 
				"        if (selection.rangeCount > 0) {\r\n" + 
				"          range = selection.getRangeAt(0);\r\n" + 
				"          var clonedSelection = range.cloneContents();\r\n" + 
				"          var div = document.createElement('div');\r\n" + 
				"          div.appendChild(clonedSelection);\r\n" + 
				"          return div.innerHTML;\r\n" + 
				"        }\r\n" + 
				"        else {\r\n" + 
				"          return '';\r\n" + 
				"        }\r\n" + 
				"      }\r\n" + 
				"      else {\r\n" + 
				"        return '';\r\n" + 
				"      }\r\n" + 
				"    }";
	}
	
	private String getInsertHtmlAtCursorJS(String html){
		return "insertHtmlAtCursor('" + html + "');"
		+ "function insertHtmlAtCursor(html) {\n"
		+ " var range, node;\n"
		+ " if (window.getSelection && window.getSelection().getRangeAt) {\n"
		+ " window.getSelection().deleteFromDocument();\n"
		+ " range = window.getSelection().getRangeAt(0);\n"
		+ " node = range.createContextualFragment(html);\n"
		+ " range.insertNode(node);\n"
		+ " } else if (document.selection && document.selection.createRange) {\n"
		+ " document.selection.createRange().pasteHTML(html);\n"
		+ " document.selection.clear();"
		+ " }\n"
		+ "}";
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
