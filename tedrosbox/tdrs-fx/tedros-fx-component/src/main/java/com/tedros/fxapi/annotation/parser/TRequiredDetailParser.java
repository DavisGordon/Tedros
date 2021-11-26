package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredDetailField;

public class TRequiredDetailParser<A extends Annotation> extends TControlFieldParser<A, TRequiredDetailField>{

	@Override
	public void parse(A annotation, TRequiredDetailField object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}