package org.tedros.tools.module.scheme.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.tedros.core.style.TStyleResourceName;
import org.tedros.core.style.TStyleResourceValue;
import org.tedros.core.style.TThemeUtil;
import org.tedros.fx.TFxKey;
import org.tedros.fx.process.TModelProcess;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.module.scheme.model.TMainColor;
import org.tedros.tools.start.TConstant;
import org.tedros.util.TColorUtil;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

import javafx.scene.paint.Color;

/**
 * Process to save the custom styles defined by the user.
 * 
 * @author Davis Gordon
 * */
public class TMainColorProcess extends TModelProcess<TMainColor>{
	
	private NumberFormat numberFormat;
	
	public TMainColorProcess() {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
	}
	
	
	@Override
	public List<TResult<TMainColor>> process(TMainColor obj) {
		TResult<TMainColor> result = new TResult<>();
		result.setValue(obj);
		try{
			saveValues(obj);
			result.setState(TState.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			result.setMessage(TFxKey.MESSAGE_ERROR);
			result.setState(TState.ERROR);
		}
		return Arrays.asList(result);
	}
	
	private void saveValues(TMainColor model) throws Exception{
		
		String propFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.DEFAULT_STYLE;
		try(FileOutputStream fos = new FileOutputStream(propFilePath)){
			Properties prop = new Properties();
			
			prop.setProperty(TStyleResourceValue.MAIN_TEXT_COLOR.name(), getHexadecimalColorValue(model.getMainCorTexto()));
			prop.setProperty(TStyleResourceValue.MAIN_COLOR.name(), getHexadecimalColorValue(model.getMainCorFundo()));
			prop.setProperty(TStyleResourceValue.MAIN_COLOR_RED.name(), getRedColor(model.getMainCorFundo()));
			prop.setProperty(TStyleResourceValue.MAIN_COLOR_GREEN.name(), getGreenColor(model.getMainCorFundo()));
			prop.setProperty(TStyleResourceValue.MAIN_COLOR_BLUE.name(), getBlueColor(model.getMainCorFundo()));
			prop.setProperty(TStyleResourceValue.MAIN_COLOR_OPACITY.name(), getDoubleConvertedValue(model.getMainOpacidade()));
			
			prop.setProperty(TStyleResourceValue.TOPBAR_TEXT_COLOR.name(), getHexadecimalColorValue(model.getNavCorTexto()));
			prop.setProperty(TStyleResourceValue.TOPBAR_COLOR.name(), getHexadecimalColorValue(model.getNavCorFundo()));
			prop.setProperty(TStyleResourceValue.TOPBAR_COLOR_RED.name(), getRedColor(model.getNavCorFundo()));
			prop.setProperty(TStyleResourceValue.TOPBAR_COLOR_GREEN.name(), getGreenColor(model.getNavCorFundo()));
			prop.setProperty(TStyleResourceValue.TOPBAR_COLOR_BLUE.name(), getBlueColor(model.getNavCorFundo()));
			prop.setProperty(TStyleResourceValue.TOPBAR_COLOR_OPACITY.name(), getDoubleConvertedValue(model.getNavOpacidade()));
			
			prop.setProperty(TStyleResourceValue.APP_TEXT_COLOR.name(), getHexadecimalColorValue(model.getAppCorTexto()));	
			prop.setProperty(TStyleResourceValue.APP_TEXT_SIZE.name(), getDoubleConvertedValue(model.getAppTamTexto()));
			
			prop.store(fos, "main color styles");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+TStyleResourceName.HEADER_STYLE.toString();
		try(FileOutputStream fos = new FileOutputStream(propFilePath)){
			Properties prop = new Properties();
			
			prop.setProperty(TStyleResourceValue.BRAND.name(), model.getBrand());
			prop.setProperty(TStyleResourceValue.INDENTANTION.name(), getDoubleConvertedValue(model.getIndentation()));
			
			File file = model.getFileLogo()!=null ? model.getFileLogo().getFile() : null;
			if(file!=null) {
				String path = TedrosFolder.MODULE_FOLDER.getFullPath()+TConstant.UUI+File.separator + file.getName(); 
				File target = new File(path);
				if(!target.exists()) {
					try {
						FileUtils.copyFile(file, target);
						prop.setProperty(TStyleResourceValue.LOGO.name(), target.getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else
					prop.setProperty(TStyleResourceValue.LOGO.name(), target.getAbsolutePath());
				
				
			}
			
			prop.store(fos, "header styles");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private String getDoubleConvertedValue(Double value){
		if(value==null)
			value = 0D;
		return numberFormat.format(value).replaceAll(",", ".");
	}
	
	private String getHexadecimalColorValue(Color color){
		return TColorUtil.toHexadecimal(color);
	}
	
	private String getRedColor(Color color){
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(color));
		return String.valueOf(rgb.getRed());
	}
	
	private String getGreenColor(Color color){
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(color));
		return String.valueOf(rgb.getGreen());
		
	}
	
	private String getBlueColor(Color color){
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(color));
		return String.valueOf(rgb.getBlue());
	}

	

	

	

}
