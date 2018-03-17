package com.tedros.fxapi.annotation.parser;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.control.TItem;

import javafx.scene.control.ComboBox;

@SuppressWarnings("rawtypes")
public class TComboBoxParser extends TAnnotationParser<TComboBoxField, ComboBox> {

	private static TComboBoxParser instance;

	private TComboBoxParser(){
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void parse(TComboBoxField annotation, ComboBox object, String... byPass)
			throws Exception {
		
		super.parse(annotation, object, "firstItemTex", "optionsList");
		
		final String firstItemText = annotation.firstItemTex();
		if(StringUtils.isNotBlank(firstItemText)){
			object.getItems().addAll(0, Arrays.asList(new TItem(iEngine.getString(firstItemText), null)));
		}
	}
	
	
	public static TComboBoxParser getInstance(){
		if(instance==null)
			instance = new TComboBoxParser();
		return instance;
	}
}
