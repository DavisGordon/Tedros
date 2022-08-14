/**
 * 
 */
package org.tedros.core.style;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.tedros.core.context.TedrosContext;
import org.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public final  class TThemeUtil {
	
	private static final String BACKGROUND_CSS = "background-image.css";
	private static final String STYLE_CSS = "custom-styles.css";
	
	public static String getCurrentTheme() {
		String propFilePath = TedrosFolder.CONF_FOLDER.getFullPath()+"theme.properties";
		Properties prop = new Properties();
		try {
			InputStream is = new FileInputStream(propFilePath);
			prop.load(is);
			is.close();
			return prop.getProperty("apply");
				
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String getThemeFolder() {
		return TedrosFolder.THEME_FOLDER.getFullPath()+ getCurrentTheme()+File.separator;
	}
	
	public static String getBackgroundFilePath() {
		return getThemeFolder()+TStyleResourceName.BACKGROUND_STYLE;
	}
	
	public static String getStyleFilePath() {
		return getThemeFolder()+TStyleResourceName.CUSTOM_STYLE;
	}
	
	public static String getBackgroundCssFilePath() {
		return getThemeFolder()+BACKGROUND_CSS;
	}
	
	public static String getStyleCssFilePath() {
		return getThemeFolder()+STYLE_CSS;
	}
	
	public static URL getBackgroundURL() {
		return TedrosContext.getExternalURLFile(TedrosFolder.THEME_FOLDER, 
				getCurrentTheme()+File.separator+BACKGROUND_CSS);
	}
	
	public static URL getStyleURL() {
		return TedrosContext.getExternalURLFile(TedrosFolder.THEME_FOLDER, 
				getCurrentTheme()+File.separator+STYLE_CSS);
	}
}
