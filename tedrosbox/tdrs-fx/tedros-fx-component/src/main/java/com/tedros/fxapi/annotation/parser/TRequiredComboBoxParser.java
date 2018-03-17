package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredComboBox;

@SuppressWarnings("rawtypes")
public class TRequiredComboBoxParser<A extends Annotation> extends TControlFieldParser<A, TRequiredComboBox>{

	private static TRequiredComboBoxParser instance;
	
	private TRequiredComboBoxParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredComboBoxParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredComboBoxParser<A>();
		return instance;	
	}

	@Override
	public void parse(A annotation, TRequiredComboBox object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}