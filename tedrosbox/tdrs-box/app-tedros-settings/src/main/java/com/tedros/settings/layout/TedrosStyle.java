package com.tedros.settings.layout;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javafx.scene.paint.Color;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.util.TColorUtil;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

public abstract class TedrosStyle {

	private TedrosStyle(){
		
	}
	
	public static String toHexadecimal(Color color){
		return TColorUtil.toHexadecimal(color);
	}
	
	
	public static void applyChanges(){
		
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"/template.css";
		File cssTemplate = new File(path);
		if(!cssTemplate.isFile())
			return;
		
		String cssContent = TFileUtil.readFile(cssTemplate);
		cssContent = cssContent.replaceAll("%TOPBAR_TEXT_COLOR%", "#FFFFFF");
		cssContent = cssContent.replaceAll("%TOPBAR_COLOR%", "transparent");
		
		Properties panelCustomProp = new Properties();
		Properties defaultProp = new Properties();
		String panelCustomPropFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.PANEL_CUSTOM_STYLE;
		String defaultFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.DEFAULT_STYLE;
		try {
			InputStream is = new FileInputStream(defaultFilePath);
			defaultProp.load(is);
			is = new FileInputStream(panelCustomPropFilePath);
			panelCustomProp.load(is);
			
			for(Object e : defaultProp.keySet()){
				String key = (String) e;
				cssContent = cssContent.replaceAll("%"+key+"%", defaultProp.getProperty(key));
				 
				if(TStyleResourceValue.ROOT_BACKGROUND.name().equals(key))
				cssContent = cssContent.replaceAll("%"+key+"%", defaultProp.getProperty(key).replaceAll(TemplateStyleId.BACKGROUND_COLOR.toString(), ""));
			}
			
			for(Object e : panelCustomProp.keySet()){
				String key = (String) e;
				cssContent = cssContent.replaceAll("%"+key+"%", panelCustomProp.getProperty(key));
			}
			
		} catch (Exception  e1) {
			e1.printStackTrace();
		}
		
		path = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"/custom-styles.css";
		File cssFile = new File(path);
		if(!cssFile.isFile())
			return;
		
		TFileUtil.saveFile(cssContent, cssFile);
		TedrosContext.reloadStyle();
		
	}
	
}
