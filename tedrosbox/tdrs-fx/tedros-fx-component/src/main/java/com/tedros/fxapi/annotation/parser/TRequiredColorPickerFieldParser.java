package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredColorPicker;

public class TRequiredColorPickerFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredColorPicker>{

	@SuppressWarnings("rawtypes")
	private static TRequiredColorPickerFieldParser instance;
	
	private TRequiredColorPickerFieldParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredColorPickerFieldParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredColorPickerFieldParser<A>();
		return instance;	
	}
	
	@Override
	public void parse(A annotation, TRequiredColorPicker object,
			String... byPass) throws Exception {
	
		super.parse(annotation, object, "+required");
	}
}