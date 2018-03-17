package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TNumberField;


@SuppressWarnings("rawtypes")
public class TNumberFieldParser<A extends Annotation> extends TControlFieldParser<A, TNumberField>{

	private static  TNumberFieldParser instance;
	
	private TNumberFieldParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TNumberFieldParser<A> getInstance(){
		if(instance==null)
			instance = new TNumberFieldParser<A>();
		return instance;	
	}
}