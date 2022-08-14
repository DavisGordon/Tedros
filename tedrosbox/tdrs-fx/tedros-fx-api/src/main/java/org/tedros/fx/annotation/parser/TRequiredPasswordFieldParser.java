package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TPasswordField;
import org.tedros.fx.control.TRequiredPasswordField;

public class TRequiredPasswordFieldParser extends TControlFieldParser<TPasswordField, TRequiredPasswordField>{

	@Override
	public void parse(TPasswordField annotation, TRequiredPasswordField object,
			String... byPass) throws Exception {
		
		super.parse(annotation, object, "+required");
	}
}