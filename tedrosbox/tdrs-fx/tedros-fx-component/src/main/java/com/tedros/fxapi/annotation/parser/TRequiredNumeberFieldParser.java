package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredNumberField;

public class TRequiredNumeberFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredNumberField>{

	@SuppressWarnings("rawtypes")
	private static TRequiredNumeberFieldParser instance;
	
	private TRequiredNumeberFieldParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredNumeberFieldParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredNumeberFieldParser<A>();
		return instance;	
	}
	
	@Override
	public void parse(A annotation, TRequiredNumberField object,
			String... byPass) throws Exception {
		// TODO Auto-generated method stub
		super.parse(annotation, object, "+zeroValidation");
	}
}