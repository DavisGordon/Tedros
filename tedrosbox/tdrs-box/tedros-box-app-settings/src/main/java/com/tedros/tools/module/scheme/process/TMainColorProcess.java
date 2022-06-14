package com.tedros.tools.module.scheme.process;

import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.tedros.core.style.TStyleResourceName;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.core.style.TThemeUtil;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.TState;
import com.tedros.fxapi.process.TModelProcess;
import com.tedros.tools.module.scheme.model.TMainColor;
import com.tedros.util.TColorUtil;

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
			result.setMessage("Dados salvo com sucesso!");
			result.setState(TState.SUCCESS);
		}catch(Exception e){
			result.setMessage(e.getMessage());
			result.setState(TState.ERROR);
		}
		return Arrays.asList(result);
	}
	
	private void saveValues(TMainColor model) throws Exception{
		
		String propFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.DEFAULT_STYLE;
		FileOutputStream fos = new FileOutputStream(propFilePath);
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
		fos.close();
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
