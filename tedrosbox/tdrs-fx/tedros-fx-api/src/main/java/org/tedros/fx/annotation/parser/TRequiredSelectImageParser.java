package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TRequiredSelectImage;

public class TRequiredSelectImageParser<A extends Annotation> extends TControlFieldParser<A, TRequiredSelectImage>{

	@Override
	public void parse(A annotation, TRequiredSelectImage object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}