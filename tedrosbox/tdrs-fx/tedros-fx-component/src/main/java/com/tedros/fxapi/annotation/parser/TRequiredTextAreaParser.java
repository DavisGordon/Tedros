package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.control.TRequiredTextArea;

public class TRequiredTextAreaParser extends TAnnotationParser<TTextAreaField, com.tedros.fxapi.control.TRequiredTextArea> {
	
	private static TRequiredTextAreaParser instance;
	
	private TRequiredTextAreaParser(){
		
	}
	
	public static  TRequiredTextAreaParser getInstance(){
		if(instance==null)
			instance = new TRequiredTextAreaParser();
		return instance;	
	}
	
	@Override
	public void parse(TTextAreaField annotation, TRequiredTextArea object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}
