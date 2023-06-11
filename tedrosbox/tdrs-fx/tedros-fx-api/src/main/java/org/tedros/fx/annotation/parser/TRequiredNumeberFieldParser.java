package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredNumberField;

public class TRequiredNumeberFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredNumberField>{

	
	
	@Override
	public void parse(A annotation, TRequiredNumberField object,
			String... byPass) throws Exception {
		super.parse(annotation, object, "+validate");
	}
}