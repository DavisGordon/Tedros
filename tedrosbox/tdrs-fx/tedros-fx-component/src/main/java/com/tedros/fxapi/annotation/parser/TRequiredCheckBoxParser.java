package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredCheckBox;

public class TRequiredCheckBoxParser<A extends Annotation> extends TControlFieldParser<A, TRequiredCheckBox>{

	@SuppressWarnings("rawtypes")
	private static TRequiredCheckBoxParser instance;
	
	private TRequiredCheckBoxParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredCheckBoxParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredCheckBoxParser<A>();
		return instance;	
	}
	
	@Override
	public void parse(A annotation, TRequiredCheckBox object, String... byPass)
			throws Exception {
		// TODO Auto-generated method stub
		super.parse(annotation, object, "+required");
	}
}