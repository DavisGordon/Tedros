package com.tedros.settings.layout.process;

import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.tedros.core.style.TStyleResourceName;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.core.style.TThemeUtil;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.process.TModelProcess;
import com.tedros.settings.layout.model.PainelModel;
import com.tedros.util.TColorUtil;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolder;

import javafx.scene.paint.Color;

/**
 * Process to save the custom styles defined by the user.
 * 
 * @author Davis Gordon
 * */
public class TedrosSettingProcess extends TModelProcess<PainelModel>{
	
	private NumberFormat numberFormat;
	
	public TedrosSettingProcess() {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
	}
	
	
	@Override
	public List<TResult<PainelModel>> process(PainelModel obj) {
		TResult<PainelModel> result = new TResult<>();
		result.setValue(obj);
		try{
			saveValues(obj);
			result.setMessage("Dados salvo com sucesso!");
			result.setResult(EnumResult.SUCESS);
		}catch(Exception e){
			result.setMessage(e.getMessage());
			result.setResult(EnumResult.ERROR);
		}
		return Arrays.asList(result);
	}
	
	private void saveValues(PainelModel model) throws Exception{
		
		String propFilePath = TThemeUtil.getThemeFolder()+TStyleResourceName.PANEL_CUSTOM_STYLE;
		FileOutputStream fos = new FileOutputStream(propFilePath);
		Properties prop = new Properties();
		
		prop.setProperty(TStyleResourceValue.FORM_CONTROL_LABEL_SIZE.name(), getDoubleConvertedValue(model.getCampoTamTitulo()));
		prop.setProperty(TStyleResourceValue.FORM_CONTROL_LABEL_COLOR.name(), getHexadecimalColorValue(model.getCampoCorTitulo()));
		prop.setProperty(TStyleResourceValue.FORM_BACKGROUND_COLOR.name(), getHexadecimalColorValue(model.getFormCorFundo()));
		prop.setProperty(TStyleResourceValue.FORM_BACKGROUND_RED.name(), getRedColor(model.getFormCorFundo()));
		prop.setProperty(TStyleResourceValue.FORM_BACKGROUND_GREEN.name(), getGreenColor(model.getFormCorFundo()));
		prop.setProperty(TStyleResourceValue.FORM_BACKGROUND_BLUE.name(), getBlueColor(model.getFormCorFundo()));
		prop.setProperty(TStyleResourceValue.FORM_BACKGROUND_OPACITY.name(), getDoubleConvertedValue(model.getFormOpacidade()));
		
		prop.setProperty(TStyleResourceValue.PANEL_TEXT_COLOR.name(), getHexadecimalColorValue(model.getPainelCorTexto()));
		prop.setProperty(TStyleResourceValue.PANEL_TEXT_SIZE.name(), getDoubleConvertedValue(model.getPainelTamTexto()));
		prop.setProperty(TStyleResourceValue.PANEL_BACKGROUND_COLOR.name(), getHexadecimalColorValue(model.getPainelCorFundo()));
		prop.setProperty(TStyleResourceValue.PANEL_BACKGROUND_RED.name(), getRedColor(model.getPainelCorFundo()));
		prop.setProperty(TStyleResourceValue.PANEL_BACKGROUND_GREEN.name(), getGreenColor(model.getPainelCorFundo()));
		prop.setProperty(TStyleResourceValue.PANEL_BACKGROUND_BLUE.name(), getBlueColor(model.getPainelCorFundo()));
		prop.setProperty(TStyleResourceValue.PANEL_BACKGROUND_OPACITY.name(), getDoubleConvertedValue(model.getPainelOpacidade()));
		
		prop.setProperty(TStyleResourceValue.READER_LABEL_COLOR.name(), getHexadecimalColorValue(model.getReaderCorTituloCampo()));
		prop.setProperty(TStyleResourceValue.READER_LABEL_SIZE.name(), getDoubleConvertedValue(model.getReaderTamTituloCampo()));
		
		prop.setProperty(TStyleResourceValue.READER_TEXT_COLOR.name(), getHexadecimalColorValue(model.getReaderCorTexto()));
		prop.setProperty(TStyleResourceValue.READER_TEXT_SIZE.name(), getDoubleConvertedValue(model.getReaderTamTexto()));
		
		
		prop.setProperty(TStyleResourceValue.READER_BACKGROUND_COLOR.name(), getHexadecimalColorValue(model.getReaderCorFundo()));
		prop.setProperty(TStyleResourceValue.READER_BACKGROUND_RED.name(), getRedColor(model.getReaderCorFundo()));
		prop.setProperty(TStyleResourceValue.READER_BACKGROUND_GREEN.name(), getGreenColor(model.getReaderCorFundo()));
		prop.setProperty(TStyleResourceValue.READER_BACKGROUND_BLUE.name(), getBlueColor(model.getReaderCorFundo()));
		prop.setProperty(TStyleResourceValue.READER_BACKGROUND_OPACITY.name(), getDoubleConvertedValue(model.getReaderOpacidade()));
		
		prop.setProperty(TStyleResourceValue.BUTTON_TEXT_COLOR.name(), getHexadecimalColorValue(model.getBotaoCorTexto()));
		prop.setProperty(TStyleResourceValue.BUTTON_TEXT_SIZE.name(), getDoubleConvertedValue(model.getBotaoTamTexto()));
		prop.setProperty(TStyleResourceValue.BUTTON_BORDER_COLOR.name(), getHexadecimalColorValue(model.getBotaoCorBorda()));
		prop.setProperty(TStyleResourceValue.BUTTON_BACKGROUND_COLOR.name(), getHexadecimalColorValue(model.getBotaoCorFundo()));
		prop.setProperty(TStyleResourceValue.BUTTON_BACKGROUND_RED.name(), getRedColor(model.getBotaoCorFundo()));
		prop.setProperty(TStyleResourceValue.BUTTON_BACKGROUND_GREEN.name(), getGreenColor(model.getBotaoCorFundo()));
		prop.setProperty(TStyleResourceValue.BUTTON_BACKGROUND_BLUE.name(), getBlueColor(model.getBotaoCorFundo()));
		prop.setProperty(TStyleResourceValue.BUTTON_BACKGROUND_OPACITY.name(), getDoubleConvertedValue(model.getBotaoOpacidade()));
		
		prop.setProperty(TStyleResourceValue.INPUT_TEXT_COLOR.name(), getHexadecimalColorValue(model.getCampoCorTexto()));
		prop.setProperty(TStyleResourceValue.INPUT_TEXT_SIZE.name(), getDoubleConvertedValue(model.getCampoTamTexto()));
		prop.setProperty(TStyleResourceValue.INPUT_BORDER_COLOR.name(), getHexadecimalColorValue(model.getCampoCorBorda()));
		prop.setProperty(TStyleResourceValue.INPUT_COLOR.name(), getHexadecimalColorValue(model.getCampoCorFundo()));
		
		
		prop.store(fos, "custom styles");
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
