package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TTextField;

public class TTTextFieldParser extends TAnnotationParser<TTextField, com.tedros.fxapi.control.TTextField> {
	
	private static TTTextFieldParser instance;
	
	private TTTextFieldParser(){
		
	}
	
	public static  TTTextFieldParser getInstance(){
		if(instance==null)
			instance = new TTTextFieldParser();
		return instance;	
	}
	
	@Override
	public void parse(TTextField annotation, com.tedros.fxapi.control.TTextField object, String... byPass)
			throws Exception {
		super.parse(annotation, object, "+maxLength");
	}
}
