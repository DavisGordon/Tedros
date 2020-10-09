package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TDetailFieldRequired;

public class TRequiredDetailListParser<A extends Annotation> extends TControlFieldParser<A, TDetailFieldRequired>{

	@SuppressWarnings("rawtypes")
	private static TRequiredDetailListParser instance;
	
	private TRequiredDetailListParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredDetailListParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredDetailListParser<A>();
		return instance;	
	}
	
	@Override
	public void parse(A annotation, TDetailFieldRequired object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}