package com.tedros.core.style;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

public enum TStyleResourceValue {
	
	MAIN_COLOR,
	MAIN_LIGHT_COLOR,
	MAIN_TEXT_COLOR,
	TOPBAR_TEXT_COLOR,
	LEFT_TOOLBAR_COLOR,
	LEFT_TOOLBAR_TEXT_COLOR,
	MAIN_PAGE_COLOR,
	ROOT_BACKGROUND, 
	ROOT_MAIN_COLOR, 
	ROOT_MAINLIGHT_COLOR,
	FOCUS_ANGLE,
	FOCUS_DISTANCE,
	CENTER,
	RADIUS,
	REPEAT,
	REFLECT, 
	COLOR_STOP,
	
	FORM_LABEL_COLOR, 
	FORM_LABEL_SIZE,
	FORM_CONTROL_LABEL_COLOR,
	FORM_CONTROL_LABEL_SIZE,
	FORM_BACKGROUND_COLOR,
	FORM_BACKGROUND_RED,
	FORM_BACKGROUND_GREEN,
	FORM_BACKGROUND_BLUE,
	FORM_BACKGROUND_OPACITY,
	
	PANEL_TEXT_COLOR,
	PANEL_TEXT_SIZE,
	PANEL_BACKGROUND_COLOR,
	PANEL_BACKGROUND_RED,
	PANEL_BACKGROUND_GREEN,
	PANEL_BACKGROUND_BLUE,
	PANEL_BACKGROUND_OPACITY,
	
	READER_LABEL_COLOR,
	READER_LABEL_SIZE,
	READER_TEXT_COLOR,
	READER_TEXT_SIZE,
	READER_BACKGROUND_COLOR,
	READER_BACKGROUND_RED,
	READER_BACKGROUND_GREEN,
	READER_BACKGROUND_BLUE,
	READER_BACKGROUND_OPACITY,
	
	BUTTON_TEXT_COLOR,
	BUTTON_TEXT_SIZE,
	BUTTON_BORDER_COLOR,
	BUTTON_BACKGROUND_COLOR,
	BUTTON_BACKGROUND_RED,
	BUTTON_BACKGROUND_GREEN,
	BUTTON_BACKGROUND_BLUE,
	BUTTON_BACKGROUND_OPACITY,
	
	INPUT_TEXT_COLOR,
	INPUT_TEXT_SIZE,
	INPUT_COLOR,
	INPUT_BORDER_COLOR
	;
	
	private TStyleResourceValue() {
		
	}
	
	static private Properties panelCustomProp;
	static private Properties defaultProp;
	static private Properties backgroundProp;
	
	public static void loadCustomValues(boolean reload){	
		if(null==panelCustomProp || reload){
			panelCustomProp = new Properties();
			String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.PANEL_CUSTOM_STYLE;
			try {
				InputStream is = new FileInputStream(propFilePath);
				
				panelCustomProp.load(is);
			} catch (Exception  e1) {
				e1.printStackTrace();
			}
		}
		if(null==backgroundProp || reload){
			backgroundProp = new Properties();
			String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.BACKGROUND_STYLE;
			try {
				InputStream is = new FileInputStream(propFilePath);
				
				backgroundProp.load(is);
			} catch (Exception  e1) {
				e1.printStackTrace();
			}
		}
		 
	}
	
	static private void buildDefault(){
		if(null==defaultProp){
			defaultProp = new Properties();
			String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.DEFAULT_STYLE;
			try {
				InputStream is = new FileInputStream(propFilePath);				
				defaultProp.load(is);
			} catch (FileNotFoundException  e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		 
	}
	
	public static TStyleResourceValue getByName(String name){
		if(StringUtils.isNotBlank(name))
			for (TStyleResourceValue e : values()) {
				if(e.name().equals(name))
					return e;
			}
		
		return null;
	}
	
	public String customStyle() {
		loadCustomValues(false);
		return getCustomValue();
	}	
	
	public String customStyle(boolean reloadValues) {
		loadCustomValues(reloadValues);
		return getCustomValue();
	}
	
	public String defaultStyle() {
		buildDefault();
		if(defaultProp.containsKey(name()))
			return  defaultProp.getProperty(name());
		return null;
	}
	
	private String getCustomValue() {
		return (panelCustomProp.containsKey(name()))
					? panelCustomProp.getProperty(name())
							: backgroundProp.containsKey(name()) 
							? backgroundProp.getProperty(name()) 
									: null;
	}
}
