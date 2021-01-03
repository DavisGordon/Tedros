package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TDetailFieldRequired;

public class TRequiredDetailListParser<A extends Annotation> extends TControlFieldParser<A, TDetailFieldRequired>{

	@Override
	public void parse(A annotation, TDetailFieldRequired object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}