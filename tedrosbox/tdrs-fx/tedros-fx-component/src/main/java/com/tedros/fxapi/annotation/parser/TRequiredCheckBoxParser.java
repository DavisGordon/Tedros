package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredCheckBox;

public class TRequiredCheckBoxParser<A extends Annotation> extends TControlFieldParser<A, TRequiredCheckBox>{

	@Override
	public void parse(A annotation, TRequiredCheckBox object, String... byPass)
			throws Exception {
		// TODO Auto-generated method stub
		super.parse(annotation, object, "+required");
	}
}