package org.tedros.ai.web;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Date;

import org.tedros.util.TDateUtil;
import org.tedros.util.TFileUtil;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TPdfUtil;
import org.tedros.util.TedrosFolder;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class TerosWebViewBridge {

	private final WebView webview;
	
	public TerosWebViewBridge(WebView webview) {
        this.webview = webview;
        WebEngine we = this.webview.getEngine(); 
        we.setJavaScriptEnabled(true);

        // 1. DEBUG: Permite ver logs de erro do JS via alert() no console Java
        we.setOnAlert(event -> {
            TLoggerUtil.info(this.getClass(), "JS Alert: " + event.getData());
        });

        // 2. CORREÇÃO DE TIMING: Injeta a ponte APENAS quando a página terminar de carregar
        we.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) we.executeScript("window");
                window.setMember("app", this);
                TLoggerUtil.info(this.getClass(), "Bridge 'app' injetada com sucesso no JS.");
            }
        });
        
        // (Opcional) Tenta injetar imediatamente caso já esteja carregado
        if (we.getLoadWorker().getState() == Worker.State.SUCCEEDED) {
             JSObject window = (JSObject) we.executeScript("window");
             window.setMember("app", this);
        }

        we.onErrorProperty().set(event -> {
            TLoggerUtil.warn(this.getClass(), "WebView error: " + event.getMessage());
            throwError("WebView error: " + event.getMessage());
        });
    }
	
	public void throwError(String message) {
		throw new RuntimeException(message);
	}
	
	public void run(String content) {
		String cleanContent = sanitizeAiOutput(content);
		TLoggerUtil.info(this.getClass(), "Enviando conteúdo para WebView: " + cleanContent);
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
	
	public void exportPdf(String htmlContent) {
		String dateTime = TDateUtil.format(new Date(), "yyyyMMdd_HHmmss");
		String fileName = "teros_response-"+dateTime+".pdf";
		String path = TedrosFolder.EXPORT_FOLDER.getFullPath() + fileName;
		try {
			TPdfUtil.convert(htmlContent, path);
			TFileUtil.open(new File(path));
		} catch (IOException e) {
			TLoggerUtil.error(this.getClass(), e.getMessage(), e);
			throw new RuntimeException("Erro ao exportar PDF: " + e.getMessage());
		}
	}
	
	public void openExternalLink(String url) {
		TLoggerUtil.info(this.getClass(), "Solicitado para abrir link externo: " + url);
        try {
            // Usa a classe Desktop para abrir o navegador padrão do sistema
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            TLoggerUtil.error(this.getClass(), e.getMessage(), e);
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