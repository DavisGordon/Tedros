package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredTextField;

public class TRequiredTextFieldParser<A extends Annotation> extends TControlFieldParser<A, TRequiredTextField>{

	@Override
	public void parse(A annotation, TRequiredTextField object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}