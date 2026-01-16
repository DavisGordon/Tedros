package org.tedros.ai.web;

import java.awt.Desktop;
import java.net.URI;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class TerosWebViewBridge {

	private final WebView webview;
	
	public TerosWebViewBridge(WebView webview) {
		this.webview = webview;
		WebEngine we = this.webview.getEngine(); 
		we.setJavaScriptEnabled(true);
		JSObject window = (JSObject) we.executeScript("window");
		window.setMember("app", this);
	}
	
	public void run(String content) {
		String cleanContent = sanitizeAiOutput(content);
		getWebEngine().executeScript("appendAIResponse(" + toJSString(cleanContent) + ")");
	}

	private String toJSString(String content) {
	    // Escapa o conteúdo para ser uma string JS válida
	    return "\"" + content.replace("\\", "\\\\")
	                          .replace("\"", "\\\"")
	                          .replace("\n", "\\n")
	                          .replace("\r", "\\r") + "\"";
	}
	
	private WebEngine getWebEngine() {
		return webview.getEngine();
	}
	
	public void openExternalLink(String url) {
        System.out.println("Solicitado para abrir link externo: " + url);
        try {
            // Usa a classe Desktop para abrir o navegador padrão do sistema
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// Método auxiliar para limpar "sujeiras" comuns do modelo
	public static String sanitizeAiOutput(String input) {
	    if (input == null) return "";
	    
	    String result = input;

	    // 1. Remove blocos de código Markdown (```html ou ```)
	    if (result.startsWith("```html")) {
	        result = result.substring(7);
	    } else if (result.startsWith("```")) {
	        result = result.substring(3);
	    }
	    if (result.endsWith("```")) {
	        result = result.substring(0, result.length() - 3);
	    }

	    // 2. Desfaz o escape de tags HTML básicas se o modelo tiver escapado tudo
	    // Isso verifica se o inicio parece um html escapado (ex: &lt;div)
	    if (result.trim().startsWith("&lt;") && result.contains("&gt;")) {
	        result = result.replace("&lt;", "<")
	                       .replace("&gt;", ">")
	                       .replace("&quot;", "\"")
	                       .replace("&amp;", "&");
	    }
	    
	    return result.trim();
	}
}