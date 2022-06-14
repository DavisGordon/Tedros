package com.tedros.tools.module.scheme.style;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javafx.scene.paint.Color;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.core.style.TThemeUtil;
import com.tedros.util.TColorUtil;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolder;

public abstract class TedrosStyleUtil {

	private TedrosStyleUtil(){
		
	}
	
	public static String toHexadecimal(Color color){
		return TColorUtil.toHexadecimal(color);
	}
	
	
	public static void applyChanges(){
		
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+"/template.css";
		File cssTemplate = new File(path);
		if(!cssTemplate.isFile())
			return;
		
		String cssContent = TFileUtil.readFile(cssTemplate);
		
		Properties panelCustomProp = new Properties();
		Properties defaultProp = new Properties();
		String panelCustomPropFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.PANEL_CUSTOM_STYLE;
		String defaultFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.DEFAULT_STYLE;
		try {
			InputStream is = new FileInputStream(defaultFilePath);
			defaultProp.load(is);
			is.close();
			is = new FileInputStream(panelCustomPropFilePath);
			panelCustomProp.load(is);
			is.close();
			for(Object e : defaultProp.keySet()){
				String key = (String) e;
				cssContent = cssContent.replaceAll("%"+key+"%", defaultProp.getProperty(key));
				 
				if(TStyleResourceValue.ROOT_BACKGROUND.name().equals(key))
				cssContent = cssContent.replaceAll("%"+key+"%", defaultProp.getProperty(key).replaceAll(CssStyle.BACKGROUND_COLOR.toString(), ""));
			}
			
			for(Object e : panelCustomProp.keySet()){
				String key = (String) e;
				cssContent = cssContent.replaceAll("%"+key+"%", panelCustomProp.getProperty(key));
			}
			
		} catch (Exception  e1) {
			e1.printStackTrace();
		}
		
		path = TThemeUtil.getThemeFolder()+"custom-styles.css";
		File cssFile = new File(path);
		if(!cssFile.isFile())
			return;
		
		TFileUtil.saveFile(cssContent, cssFile);
		TedrosContext.reloadStyle();
		
	}
	
}
