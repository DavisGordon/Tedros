package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredTextField;

public class TRequiredTextFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredTextField>{

	@Override
	public void parse(A annotation, TRequiredTextField object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}