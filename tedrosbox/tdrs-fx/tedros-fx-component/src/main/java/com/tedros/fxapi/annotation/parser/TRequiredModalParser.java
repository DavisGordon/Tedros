package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TModalRequired;

public class TRequiredModalParser<A extends Annotation> extends TControlFieldParser<A, TModalRequired>{

	@SuppressWarnings("rawtypes")
	private static TRequiredModalParser instance;
	
	private TRequiredModalParser(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <A extends Annotation> TRequiredModalParser<A> getInstance(){
		if(instance==null)
			instance = new TRequiredModalParser<A>();
		return instance;	
	}
	
	@Override
	public void parse(A annotation, TModalRequired object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}