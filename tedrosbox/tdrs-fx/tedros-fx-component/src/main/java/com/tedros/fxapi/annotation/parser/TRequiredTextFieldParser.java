package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredTextField;

public class TRequiredTextFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredTextField>{

	@SuppressWarnings("rawtypes")
	private static TRequiredTextFieldParser instance;
	
	private TRequiredTextFieldParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredTextFieldParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredTextFieldParser<A>();
		return instance;	
	}
	
	@Override
	public void parse(A annotation, TRequiredTextField object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}