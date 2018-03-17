package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredDatePicker;

public class TRequiredDatePickerParser<A extends Annotation> extends TControlFieldParser<A, TRequiredDatePicker>{

	@SuppressWarnings("rawtypes")
	private static TRequiredDatePickerParser instance;
	
	private TRequiredDatePickerParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredDatePickerParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredDatePickerParser<A>();
		return instance;	
	}
	
	@Override
	public void parse(A annotation, TRequiredDatePicker object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}