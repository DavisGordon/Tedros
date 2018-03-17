package com.tedros.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public final class TResourceUtil {

	private TResourceUtil() {
		
	}
	
	public static Properties getPropertiesFromConfFolder(String propertieFileName){
		Properties prop = null;
		try {
			String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+propertieFileName;
			InputStream is = new FileInputStream(propFilePath);
			prop = new Properties();
			prop.load(is);
		} catch (Exception  e1) {
			e1.printStackTrace();
		}
		return prop;
	}
	
	public static ResourceBundle getResourceBundle(String propertieName){
		return ResourceBundle.getBundle(propertieName);
	}
	
	
}
