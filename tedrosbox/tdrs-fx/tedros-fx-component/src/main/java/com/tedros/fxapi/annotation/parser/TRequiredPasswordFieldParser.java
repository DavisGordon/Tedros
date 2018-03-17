package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.control.TRequiredPasswordField;

public class TRequiredPasswordFieldParser extends TControlFieldParser<TPasswordField, TRequiredPasswordField>{

	private static TRequiredPasswordFieldParser instance;
	
	private TRequiredPasswordFieldParser(){
		
	}
	
	public static TRequiredPasswordFieldParser getInstance(){
		if(instance==null)
			instance = new TRequiredPasswordFieldParser();
		return instance;	
	}
	
	@Override
	public void parse(TPasswordField annotation, TRequiredPasswordField object,
			String... byPass) throws Exception {
		
		super.parse(annotation, object, "+required");
	}
}