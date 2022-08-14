package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredDetailField;

public class TRequiredDetailParser<A extends Annotation> extends TControlFieldParser<A, TRequiredDetailField>{

	@Override
	public void parse(A annotation, TRequiredDetailField object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}