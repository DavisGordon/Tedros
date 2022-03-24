package com.tedros.core.style;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolder;

/**
 * The enum with possible values for style resources
 * */
public enum TStyleResourceValue {
	
	MAIN_COLOR,
	MAIN_COLOR_RED,
	MAIN_COLOR_GREEN,
	MAIN_COLOR_BLUE,
	MAIN_COLOR_OPACITY,
	MAIN_TEXT_COLOR,
	TOPBAR_COLOR,
	TOPBAR_COLOR_RED,
	TOPBAR_COLOR_GREEN,
	TOPBAR_COLOR_BLUE,
	TOPBAR_COLOR_OPACITY,
	TOPBAR_TEXT_COLOR,
	
	APP_TEXT_COLOR,
	APP_TEXT_SIZE,
	
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
			String propFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.PANEL_CUSTOM_STYLE;
			try {
				InputStream is = new FileInputStream(propFilePath);
				panelCustomProp.load(is);
				is.close();
			} catch (Exception  e1) {
				e1.printStackTrace();
			}
		}
		if(null==backgroundProp || reload){
			backgroundProp = new Properties();
			String propFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.BACKGROUND_STYLE;
			try {
				InputStream is = new FileInputStream(propFilePath);
				backgroundProp.load(is);
				is.close();
			} catch (Exception  e1) {
				e1.printStackTrace();
			}
		}
		 
	}
	
	static private void buildDefault(boolean reload){
		if(null==defaultProp || reload){
			defaultProp = new Properties();
			String propFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.DEFAULT_STYLE;
			try {
				InputStream is = new FileInputStream(propFilePath);				
				defaultProp.load(is);
				is.close();
			}catch (Exception e) {
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
		return defaultStyle(false);
	}
	
	public String defaultStyle(boolean reloadValues) {
		buildDefault(reloadValues);
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
