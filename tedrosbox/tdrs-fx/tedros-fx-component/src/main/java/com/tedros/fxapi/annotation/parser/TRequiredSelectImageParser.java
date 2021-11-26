package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredSelectImage;

public class TRequiredSelectImageParser<A extends Annotation> extends TControlFieldParser<A, TRequiredSelectImage>{

	@Override
	public void parse(A annotation, TRequiredSelectImage object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}