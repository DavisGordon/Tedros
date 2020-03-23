package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TTextAreaField;

public class TTextAreaFieldParser extends TAnnotationParser<TTextAreaField, com.tedros.fxapi.control.TTextAreaField> {
	
	private static TTextAreaFieldParser instance;
	
	private TTextAreaFieldParser(){
		
	}
	
	public static  TTextAreaFieldParser getInstance(){
		if(instance==null)
			instance = new TTextAreaFieldParser();
		return instance;	
	}
}
