package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.CheckBox;

import com.tedros.fxapi.annotation.control.TCheckBoxField;

public class TCheckBoxParser extends TAnnotationParser<TCheckBoxField, CheckBox> {

	private static TCheckBoxParser instance;

	private TCheckBoxParser(){
		
	}
	
	public static TCheckBoxParser getInstance(){
		if(instance==null)
			instance = new TCheckBoxParser();
		return instance;
	}
}
