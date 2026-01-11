package org.tedros;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.tedros.core.style.TThemeUtil;
import org.tedros.util.TLoggerUtil;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

class TedrosBoxStyleHelper {
	
	private TedrosBoxStyleHelper() {
	}
	
	public static void reloadStyle(Scene scene, BorderPane root){
    	
    	final String customStyleCssUrl = TThemeUtil.getStyleURL().toExternalForm();
    	final String backgroundCssUrl = TThemeUtil.getBackgroundURL().toExternalForm();
    	
    	removeCss(scene, customStyleCssUrl);
    	File basckGroundCss = new File(TThemeUtil.getBackgroundCssFilePath());
		try {
			TLoggerUtil.info(TedrosBox.class, FileUtils.readFileToString(basckGroundCss, Charset.defaultCharset()));
		} catch (IOException e) {
			TLoggerUtil.error(TedrosBox.class, e.getMessage(), e);
		}
    	if(!basckGroundCss.exists()){
			removeCss(scene, backgroundCssUrl);
		}else{
			removeCss(scene, backgroundCssUrl);
			scene.getStylesheets().add(backgroundCssUrl);
		}
		scene.getStylesheets().add(customStyleCssUrl);
    	com.sun.javafx.css.StyleManager.getInstance().addUserAgentStylesheet(scene, customStyleCssUrl);
    	root.setId("t-tedros-color");
    }
	
	private static void removeCss(Scene scene, String url) {
		if(scene.getStylesheets().contains(url))
			scene.getStylesheets().remove(url);
	}

}
