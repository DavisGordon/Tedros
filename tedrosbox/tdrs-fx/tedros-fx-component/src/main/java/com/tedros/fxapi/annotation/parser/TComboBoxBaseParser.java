package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.ComboBoxBase;

import com.tedros.fxapi.annotation.scene.control.TComboBoxBase;

@SuppressWarnings("rawtypes")
public class TComboBoxBaseParser extends TAnnotationParser<TComboBoxBase, ComboBoxBase> {
	
	private static TComboBoxBaseParser instance;
	
	private TComboBoxBaseParser(){
		
	}
	
	public static  TComboBoxBaseParser getInstance(){
		if(instance==null)
			instance = new TComboBoxBaseParser();
		return instance;	
	}
}
