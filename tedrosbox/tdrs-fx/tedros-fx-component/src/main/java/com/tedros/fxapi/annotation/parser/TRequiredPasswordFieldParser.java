package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.control.TRequiredPasswordField;

public class TRequiredPasswordFieldParser extends TControlFieldParser<TPasswordField, TRequiredPasswordField>{

	@Override
	public void parse(TPasswordField annotation, TRequiredPasswordField object,
			String... byPass) throws Exception {
		
		super.parse(annotation, object, "+required");
	}
}