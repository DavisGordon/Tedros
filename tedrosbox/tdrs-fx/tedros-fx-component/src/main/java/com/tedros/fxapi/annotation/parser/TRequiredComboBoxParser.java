package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.control.TRequiredComboBox;

@SuppressWarnings("rawtypes")
public class TRequiredComboBoxParser<A extends Annotation> extends TControlFieldParser<A, TRequiredComboBox>{


	@Override
	public void parse(A annotation, TRequiredComboBox object, String... byPass) throws Exception {
		super.parse(annotation, object, "+required");
	}
}