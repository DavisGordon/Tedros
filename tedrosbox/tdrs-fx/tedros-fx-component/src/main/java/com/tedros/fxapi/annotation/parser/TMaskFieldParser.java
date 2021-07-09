package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TMaskField;

public class TMaskFieldParser extends TAnnotationParser<TMaskField, com.tedros.fxapi.control.TMaskField> {
	
	private static TMaskFieldParser instance;
	
	private TMaskFieldParser(){
		
	}
	
	public static  TMaskFieldParser getInstance(){
		if(instance==null)
			instance = new TMaskFieldParser();
		return instance;	
	}
	
	@Override
	public void parse(TMaskField annotation, com.tedros.fxapi.control.TMaskField object, String... byPass) throws Exception {
		super.parse(annotation, object, "node", "control", "pane");
	}
}
