package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredModal;

public class TRequiredModalParser<A extends Annotation> extends TControlFieldParser<A, TRequiredModal>{

	@Override
	public void parse(A annotation, TRequiredModal object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}