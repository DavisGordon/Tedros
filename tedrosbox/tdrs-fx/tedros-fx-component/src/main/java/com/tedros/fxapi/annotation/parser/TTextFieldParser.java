package com.tedros.fxapi.annotation.parser;

import javafx.scene.control.TextField;

import com.tedros.fxapi.annotation.control.TTextField;

public class TTextFieldParser extends TAnnotationParser<TTextField, TextField> {
	
	private static TTextFieldParser instance;
	
	private TTextFieldParser(){
		
	}
	
	public static  TTextFieldParser getInstance(){
		if(instance==null)
			instance = new TTextFieldParser();
		return instance;	
	}
	
}
